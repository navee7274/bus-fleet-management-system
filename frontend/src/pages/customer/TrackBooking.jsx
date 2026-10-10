import { useState } from "react";
import { useNavigate } from "react-router-dom";
import bookingService from "../../services/bookingService";
import "../../styles/CustomerBooking.css";

import "../../styles/LandingPage.css";

import Navbar from "../../components/CustomerNavBar";
import Footer from "../../components/CustomerFooter";

function TrackBooking() {
  const navigate = useNavigate();
  const [bookingNumber, setBookingNumber] = useState("");
  const [booking, setBooking] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const trackBooking = async (event) => {
    event.preventDefault();
    const number = bookingNumber.trim();
    if (!number) {
      setError("Enter your booking number to continue.");
      return;
    }

    setLoading(true);
    setError("");
    setBooking(null);

    try {
      const lookup = bookingService.trackBooking || bookingService.getBookingByNumber || bookingService.getBookingById || bookingService.getBooking;

      if (!lookup) throw new Error("Booking tracking is currently unavailable.");

      const response = await lookup.call(bookingService, number);
      const result = response?.data ?? response;
      const details = result?.booking ?? result;
      if (!details || typeof details !== "object") {
        throw new Error("No booking was found with that number.");
      }
      setBooking(details);
    } catch (err) {
      setError(err?.response?.data?.message || err?.message || "Unable to find this booking. Check the number and try again.");
    } finally {
      setLoading(false);
    }
  };

  const displayValue = (...values) => {
    const value = values.find((item) => item !== undefined && item !== null && item !== "");
    if (!value) return "—";
    if (typeof value === "object") return value.name || value.label || value.city || "—";
    return String(value);
  };

  const status = displayValue(booking?.status, booking?.bookingStatus);
  const fields = [
    ["Passenger", displayValue(booking?.passengerName, booking?.customerName, booking?.user?.name)],
    ["Route", displayValue(booking?.routeName, booking?.route, booking?.trip?.route)],
    ["Travel date", displayValue(booking?.travelDate, booking?.journeyDate, booking?.trip?.departureDate)],
    ["Departure", displayValue(booking?.departureTime, booking?.trip?.departureTime)],
    ["Seat(s)", displayValue(booking?.seats, booking?.seatNumbers, booking?.seatNumber)],
    ["Total", displayValue(booking?.totalAmount, booking?.amount, booking?.fare)],
  ];

  return (
    <div className="customer-booking-page">
      <Navbar />

      <main style={{ maxWidth: 1100, margin: "0 auto", padding: "48px 20px 64px", width: "100%", boxSizing: "border-box" }}>
        <header style={{ marginBottom: 28 }}>
          <p style={{ color: "#2563eb", fontWeight: 700, margin: "0 0 8px" }}>CUSTOMER PORTAL</p>
          <h1 style={{ margin: 0, color: "#172554" }}>Track your booking</h1>
          <p style={{ color: "#64748b", margin: "10px 0 0" }}>Enter your booking number to view your trip details and status.</p>
        </header>

        <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(300px, 1fr))", gap: 24, alignItems: "start" }}>
          <section
            style={{ background: "#fff", border: "1px solid #e2e8f0", borderRadius: 16, padding: 24, boxShadow: "0 8px 24px rgba(15, 23, 42, 0.06)" }}
          >
            <h2 style={{ margin: "0 0 8px", color: "#172554", fontSize: 20 }}>Find a booking</h2>
            <p style={{ color: "#64748b", margin: "0 0 22px", lineHeight: 1.5 }}>Your booking number is shown on your confirmation.</p>
            <form onSubmit={trackBooking}>
              <label htmlFor="booking-number" style={{ display: "block", fontWeight: 600, marginBottom: 8, color: "#334155" }}>
                Booking number
              </label>
              <input
                id="booking-number"
                type="text"
                value={bookingNumber}
                onChange={(event) => setBookingNumber(event.target.value)}
                placeholder="e.g. BK-123456"
                autoComplete="off"
                style={{
                  width: "100%",
                  boxSizing: "border-box",
                  padding: "12px 14px",
                  border: "1px solid #cbd5e1",
                  borderRadius: 8,
                  fontSize: 16,
                  marginBottom: 14,
                }}
              />
              <button
                type="submit"
                disabled={loading}
                style={{
                  width: "100%",
                  border: 0,
                  borderRadius: 8,
                  padding: "12px 16px",
                  background: loading ? "#94a3b8" : "#2563eb",
                  color: "#fff",
                  fontWeight: 700,
                  cursor: loading ? "wait" : "pointer",
                }}
              >
                {loading ? "Searching…" : "Track booking"}
              </button>
            </form>
            {error && (
              <p role="alert" style={{ color: "#b91c1c", margin: "14px 0 0" }}>
                {error}
              </p>
            )}
          </section>

          <section
            aria-live="polite"
            style={{
              background: "#fff",
              border: "1px solid #e2e8f0",
              borderRadius: 16,
              padding: 24,
              boxShadow: "0 8px 24px rgba(15, 23, 42, 0.06)",
              minHeight: 270,
            }}
          >
            <div style={{ display: "flex", justifyContent: "space-between", gap: 12, alignItems: "start", marginBottom: 20 }}>
              <div>
                <h2 style={{ margin: "0 0 6px", color: "#172554", fontSize: 20 }}>Booking details</h2>
                <p style={{ color: "#64748b", margin: 0 }}>
                  {booking
                    ? `Booking ${displayValue(booking.bookingNumber, booking.bookingNo, booking.id)}`
                    : "Your booking information will appear here."}
                </p>
              </div>
              {booking && (
                <span style={{ background: "#eff6ff", color: "#1d4ed8", borderRadius: 999, padding: "6px 10px", fontSize: 13, fontWeight: 700 }}>
                  {status}
                </span>
              )}
            </div>

            {booking ? (
              <>
                <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(130px, 1fr))", gap: "18px 14px" }}>
                  {fields.map(([label, value]) => (
                    <div key={label}>
                      <div style={{ color: "#64748b", fontSize: 13, marginBottom: 5 }}>{label}</div>
                      <div style={{ color: "#1e293b", fontWeight: 600, overflowWrap: "anywhere" }}>{value}</div>
                    </div>
                  ))}
                </div>
                <div style={{ display: "flex", flexWrap: "wrap", gap: 10, borderTop: "1px solid #e2e8f0", marginTop: 22, paddingTop: 18 }}>
                  <button
                    type="button"
                    onClick={() => navigate("/customer/bookings")}
                    style={{
                      border: 0,
                      borderRadius: 8,
                      padding: "10px 14px",
                      background: "#2563eb",
                      color: "#fff",
                      fontWeight: 600,
                      cursor: "pointer",
                    }}
                  >
                    My bookings
                  </button>
                  <button
                    type="button"
                    onClick={() => {
                      setBooking(null);
                      setBookingNumber("");
                      setError("");
                    }}
                    style={{
                      border: "1px solid #cbd5e1",
                      borderRadius: 8,
                      padding: "10px 14px",
                      background: "#fff",
                      color: "#334155",
                      fontWeight: 600,
                      cursor: "pointer",
                    }}
                  >
                    Track another
                  </button>
                </div>
              </>
            ) : (
              <div style={{ minHeight: 150, display: "grid", placeItems: "center", textAlign: "center", color: "#94a3b8", padding: 16 }}>
                <p style={{ margin: 0 }}>
                  Enter a booking number and select <strong>Track booking</strong> to see trip status, route, and passenger details.
                </p>
              </div>
            )}
          </section>
        </div>
      </main>

      <Footer />
    </div>
  );
}

export default TrackBooking;
