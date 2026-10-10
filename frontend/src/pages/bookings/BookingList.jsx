import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import bookingService from "../../services/bookingService";
import Loading from "../../components/Loading";
import Button from "../../components/Button";
import BookingTable from "../../components/BookingTable";
import "../../styles/Booking.css";

function BookingList() {
  const navigate = useNavigate();

  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    loadBookings();
  }, []);

  const loadBookings = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await bookingService.getAllBookings();
      setBookings(data);
    } catch (error) {
      console.error(error);
      setError("Failed to load bookings.");
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <Loading message="Loading bookings..." />;
  }

  /* Filtering Pending Bookings */
  const pendingBookings = bookings.filter((booking) => booking.status === "PENDING");

  /* Filtering Payment Pending Bookings */
  const paymentPendingBookings = bookings.filter((booking) => booking.status === "PAYMENT_PENDING");

  /* Filtering Confirmed Bookings */
  const confirmedBookings = bookings.filter((booking) => booking.status === "CONFIRMED");

  /* Filtering Completed Bookings */
  const completedBookings = bookings.filter((booking) => booking.status === "COMPLETED");

  /* Filtering Completed Bookings */
  const cancelledRejectedBookings = bookings.filter((booking) => booking.status === "REJECTED" || booking.status === "CANCELLED");

  return (
    <div className="journey-page">
      {/* Header */}
      <div className="page-header">
        <div>
          <h1>Booking Management</h1>
          <p>Manage your journey bookings</p>
        </div>

        <Button onClick={() => navigate("/bookings/add")}>+ Add Booking</Button>
      </div>

      {/* Error */}
      {error && <div className="error-message">{error}</div>}

      {/* Pending Table */}
      <h3 class="booking-table-title">Pending</h3>
      <BookingTable bookings={pendingBookings} emptyStateMessage="Pending Bookings Will Appear Here" />

      {/* Payment Pending Table */}
      <h3 className="booking-table-title">Awaiting Payment</h3>
      <BookingTable bookings={paymentPendingBookings} emptyStateMessage="Payment Pending Bookings Will Appear Here" />

      {/* Confirmed Table */}
      <h3 className="booking-table-title">Confirmed</h3>
      <BookingTable bookings={confirmedBookings} emptyStateMessage="Confirmed Bookings Will Appear Here" />

      {/* Completed Table */}
      <h3 className="booking-table-title">Completed</h3>
      <BookingTable bookings={completedBookings} emptyStateMessage="Completed Bookings Will Appear Here" />

      {/* Cancelled/Rejected Table */}
      <h3 className="booking-table-title">Cancelled/Rejected</h3>
      <BookingTable bookings={cancelledRejectedBookings} emptyStateMessage="Cancelled/Rejected Bookings Will Appear Here" />
    </div>
  );
}

export default BookingList;
