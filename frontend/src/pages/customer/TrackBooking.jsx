import { useState } from "react";
import { useNavigate } from "react-router-dom";
import bookingService from "../../services/bookingService";
import "../../styles/CustomerBooking.css";
import "../../styles/LandingPage.css";

import Button from "../../components/Button";

import Navbar from "../../components/CustomerNavBar";
import Footer from "../../components/CustomerFooter";

function TrackBooking() {
  const navigate = useNavigate();

  const [bookingNumber, setBookingNumber] = useState("");
  const [booking, setBooking] = useState(null);
  const [paymentMethod, setPaymentMethod] = useState("");
  const [paymentStatus, setPaymentStatus] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const trackBooking = async (e) => {
    e.preventDefault();

    if (!bookingNumber.trim()) {
      setError("Enter your booking number.");
      return;
    }

    setLoading(true);
    setError("");
    setBooking(null);

    try {
      const response = await bookingService.getById(bookingNumber.trim());

      const bookingData = response?.booking ?? response;
      setBooking(bookingData);

      await getPaymentMethod(bookingData.bookingID);

      nextStep();
    } catch (err) {
      setError(err?.response?.data?.message || "Booking not found.");
    } finally {
      setLoading(false);
    }
  };

  const getPaymentMethod = async (bookingID) => {
    try {
      const paymentData = await bookingService.getPaymentMethod(bookingID);

      setPaymentMethod(paymentData.paymentMethod);
      setPaymentStatus(paymentData.paymentStatus);
    } catch (err) {
      setPaymentMethod(null);
      setPaymentStatus(null);
    }
  };

  const makePayment = async (e) => {
    e?.preventDefault();

    if (!booking) return;

    setLoading(true);
    setError("");

    try {
      // Ask the backend to generate the payment payload
      const payload = await bookingService.getPaymentData(booking.bookingID);

      if (!window.payhere) {
        throw new Error("PayHere SDK failed to load.");
      }

      // Payment completed doesn't necessarily mean success.
      window.payhere.onCompleted = (orderId) => {
        setError(`Payment submitted for ${orderId}. ` + "Please check your booking status shortly.");
      };

      window.payhere.onDismissed = () => {
        setError("Payment window closed.");
      };

      window.payhere.onError = (error) => {
        setError(`PayHere error: ${error}`);
      };

      // Map your Java DTO fields to PayHere's expected names
      const payment = {
        sandbox: true, // Use false for live payments
        merchant_id: payload.merchantId,
        return_url: payload.returnUrl,
        cancel_url: payload.cancelUrl,
        notify_url: payload.notifyUrl,
        order_id: payload.orderId,
        items: payload.items,
        amount: payload.amount,
        currency: payload.currency,
        hash: payload.hash,
        first_name: payload.firstName,
        last_name: payload.lastName,
        email: payload.email,
        phone: payload.phone,
        address: payload.address,
        city: payload.city,
        country: payload.country,
      };

      window.payhere.startPayment(payment);
    } catch (err) {
      setError(err?.response?.data?.message || err?.message || "Unable to initiate payment.");
    } finally {
      setLoading(false);
    }
  };

  const markPayment = async (e) => {
    e?.preventDefault();

    setLoading(true);
    setError("");

    try {
      await bookingService.markPaymentDone(booking.bookingID);

      window.location.reload();
    } catch (err) {
      setError(err?.response?.data?.message || "Failed to mark payment.");
      setLoading(false);
    }
  };

  const [step, setStep] = useState(1);

  const nextStep = () => {
    setError("");
    setStep((prev) => Math.min(prev + 1, 2));
  };

  const prevStep = () => {
    setError("");
    setStep((prev) => Math.max(prev - 1, 1));
  };

  const handleCancel = () => {
    navigate("/");
  };

  const handleDone = () => {
    navigate("/");
  };

  const formatDate = (date) =>
    date
      ? new Date(date)
          .toLocaleString("en-US", {
            month: "short",
            day: "numeric",
            hour: "2-digit",
            minute: "2-digit",
            hour12: false,
          })
          .replace(",", " •")
      : "—";

  const formatPrice = (price) => {
    const value = price?.parsedValue ?? price?.source ?? price;

    return value != null && value !== "" ? `Rs. ${Number(value).toLocaleString("en-LK")}` : "—";
  };

  const fields = booking
    ? [
        ["Passenger", `${booking.customerFirstName} ${booking.customerLastName}`],
        ["Route", `${booking.startLocation} → ${booking.destination}`],
        ["Bus", booking.busRegistrationNo],
        ["Start", formatDate(booking.startDateTime)],
        ["End", formatDate(booking.endDateTime)],
        ["Passengers", booking.passengerCount],
        ["Estimated cost", formatPrice(booking.estimatedCost)],
        ["Final price", formatPrice(booking.finalPrice)],
        ["Advance amount", formatPrice(booking.advanceAmount)],
      ]
    : [];

  return (
    <div className="customer-booking-page">
      <Navbar />

      <main className="track-booking-container">
        <header className="track-booking-header">
          <h1>Track your booking</h1>
          <p>Enter your booking ID to view your booking details.</p>
        </header>
        {error && (
          <div className="booking-error" role="alert">
            {error}
          </div>
        )}

        <div className="track-booking-grid">
          {step === 1 && (
            <section className="booking-card track-search-card">
              <h2>Find a booking</h2>
              <p className="track-booking-description">Enter the booking ID from your confirmation.</p>

              <form onSubmit={trackBooking}>
                <div className="form-group">
                  <label htmlFor="booking-number">Booking ID</label>
                  <input
                    id="booking-number"
                    type="number"
                    min="1"
                    value={bookingNumber}
                    onChange={(e) => setBookingNumber(e.target.value)}
                    placeholder="Enter booking ID"
                    required
                  />
                </div>
                <div className="button-row">
                  <Button onClick={handleCancel} className="secondary-button">
                    Cancel
                  </Button>
                  <Button onClick={trackBooking} disabled={loading}>
                    {loading ? "Searching..." : "Track"}
                  </Button>
                </div>
              </form>
            </section>
          )}

          {step === 2 && (
            <section className="booking-card track-details-card">
              <h2>Booking details</h2>

              {booking ? (
                <>
                  <div className="track-booking-subtext">
                    <p>
                      Number: #<strong>{booking.bookingID}</strong>
                    </p>

                    <div className={`status status-${booking.status.toLowerCase().replaceAll("_", "-")}`}>
                      {booking.status
                        .toLowerCase()
                        .replaceAll("_", " ")
                        .replace(/\b\w/g, (c) => c.toUpperCase()) || "—"}
                    </div>
                  </div>
                  <div className="track-booking-fields">
                    {fields.map(([label, value]) => (
                      <div className="track-booking-field" key={label}>
                        <span>{label}</span>
                        <strong>{value ?? "—"}</strong>
                      </div>
                    ))}
                  </div>
                  {booking.status == "PAYMENT_PENDING" && paymentMethod == "Bank Transfer" ? (
                    <div class="bank-transfer-details">
                      <h3>Bank Transfer Instructions</h3>
                      <p>Transfer the advance payment to the bank account below. </p>
                      <br />
                      <p>Bank: Bank of Ceylon</p>
                      <p>Account Name: Tharusha Tours</p>
                      <p>Account Number: 1234567890</p>
                      <p>Branch: Colombo</p>
                      <br />
                      <p>Amount: {formatPrice(booking.advanceAmount)}</p>
                      <p>Payment Reference: BOOKING-{booking.bookingID}</p>
                      <br />
                      <p class="notice">Please include this payment reference when making your bank transfer to help us verify your payment.</p>
                      <br />
                      <p>
                        Once you've completed the transfer, click <strong>Submit Payment Confirmation</strong> so we can verify your payment.
                      </p>
                    </div>
                  ) : null}

                  <div className="track-booking-actions"></div>
                  <div className="button-row">
                    <Button
                      type="button"
                      className="secondary-button"
                      onClick={() => {
                        setBooking(null);
                        setBookingNumber("");
                        setError("");
                        prevStep();
                      }}
                    >
                      Track Another
                    </Button>
                    {booking.status == "PAYMENT_PENDING" ? (
                      <>
                        {paymentMethod == "Card Payment" ? (
                          <>
                            <Button type="button" onClick={makePayment} disabled={loading}>
                              {loading ? "Preparing payment..." : "Pay Advance"}
                            </Button>
                          </>
                        ) : (
                          <>
                            <Button type="button" onClick={markPayment} disabled={loading || paymentStatus == "PROCESSING"}>
                              {loading ? "Sending..." : paymentStatus === "PROCESSING" ? "Payment Under Review" : "Submit Payment Confirmation"}
                            </Button>
                          </>
                        )}
                      </>
                    ) : (
                      <>
                        <Button type="button" onClick={handleDone}>
                          Done
                        </Button>
                      </>
                    )}
                  </div>
                </>
              ) : (
                <>
                  <div className="track-booking-empty">
                    <p>{loading ? "Loading booking details..." : "Your booking details will appear here."}</p>
                  </div>
                  <div className="button-row">
                    <Button
                      type="button"
                      className="secondary-button"
                      onClick={() => {
                        setBooking(null);
                        setBookingNumber("");
                        setError("");
                        prevStep();
                      }}
                    >
                      Track Another
                    </Button>

                    <Button type="button" onClick={handleDone}>
                      Done
                    </Button>
                  </div>
                </>
              )}
            </section>
          )}
        </div>
      </main>

      <Footer />
    </div>
  );
}

export default TrackBooking;
