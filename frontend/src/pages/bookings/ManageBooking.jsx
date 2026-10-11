import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import bookingService from "../../services/bookingService";
import Loading from "../../components/Loading";
import Button from "../../components/Button";
import "../../styles/Booking.css";
import BookingActions from "../../components/BookingActions";

function ManageBooking() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [booking, setBooking] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [payment, setPayment] = useState(null);

  useEffect(() => {
    const loadBooking = async () => {
      try {
        setLoading(true);

        const data = await bookingService.getById(id);
        setBooking(data);
        await getPaymentMethod(data.bookingID);
      } catch (err) {
        console.error(err);
        setError("Failed to load booking.");
      } finally {
        setLoading(false);
      }
    };

    loadBooking();
  }, [id]);

  const getPaymentMethod = async (bookingID) => {
    try {
      const paymentData = await bookingService.getPaymentMethod(bookingID);

      setPayment(paymentData);
    } catch (err) {
      setPayment(null);
    }
  };

  if (loading) {
    return <Loading />;
  }

  if (error) {
    return (
      <div className="booking-manage-page">
        <div className="error-message">{error}</div>

        <Button onClick={() => navigate("/bookings")}>Back to Bookings</Button>
      </div>
    );
  }

  if (!booking) {
    return (
      <div className="booking-manage-page">
        <div className="empty-state">
          <h3>Booking not found</h3>

          <Button onClick={() => navigate("/bookings")}>Back to Bookings</Button>
        </div>
      </div>
    );
  }

  const formatDate = (dateTime) => {
    return new Date(dateTime).toLocaleDateString("en-GB", {
      day: "2-digit",
      month: "short",
    });
  };

  const formatTime = (dateTime) =>
    new Date(dateTime).toLocaleTimeString("en-GB", {
      hour: "2-digit",
      minute: "2-digit",
    });

  const formatPrice = (price) => {
    const value = price?.parsedValue ?? price?.source ?? price;

    return value != null && value !== "" && value !== 0 ? `Rs. ${Number(value).toLocaleString("en-LK")}` : "—";
  };

  return (
    <div className="booking-manage-page">
      <div className="booking-manage-header">
        <div>
          <h1>Booking #{booking.bookingID.toString().padStart(8, "0")}</h1>
          <p>Manage booking</p>
        </div>

        <div className="booking-header-actions">
          <Button className="btn-secondary" onClick={() => navigate("/bookings")}>
            Back
          </Button>
        </div>
      </div>
      <h3>Customer</h3>
      <div className="booking-details-card grid-four">
        <div className="booking-detail-item">
          <span>Customer Name</span>
          <strong>{booking.customerFirstName + " " + booking.customerLastName}</strong>
        </div>

        <div className="booking-detail-item">
          <span>Customer Contact</span>
          <strong>{booking.customerContactPhone}</strong>
          <strong>{booking.customerContactEmail}</strong>
        </div>

        <div className="booking-detail-item">
          <span>Customer Address</span>
          <strong>{booking.customerAddress}</strong>
        </div>

        <div className="booking-detail-item">
          <span>Customer City</span>
          <strong>{booking.customerCity}</strong>
        </div>
      </div>
      <h3>Journey</h3>
      <div className="booking-details-card grid-four">
        <div className="booking-detail-item">
          <span>Start</span>
          <strong>{booking.startLocation}</strong>
        </div>

        <div className="booking-detail-item">
          <span>Destination</span>
          <strong>{booking.destination}</strong>
        </div>
        <div className="booking-detail-item">
          <span>Start Time</span>
          <strong>
            {formatDate(booking.startDateTime)} • {formatTime(booking.startDateTime)}
          </strong>
        </div>

        <div className="booking-detail-item">
          <span>End Time</span>
          <strong>
            {formatDate(booking.endDateTime)} • {formatTime(booking.endDateTime)}
          </strong>
        </div>
      </div>
      <h3>Pricing</h3>
      <div className="booking-details-card grid-four">
        <div className="booking-detail-item">
          <span>Estimated Cost</span>
          <strong>{formatPrice(booking.estimatedCost)}</strong>
        </div>

        <div className="booking-detail-item">
          <span>Final Price</span>
          <strong>{formatPrice(booking.finalPrice)}</strong>
        </div>
        <div className="booking-detail-item">
          <span>Advance Amount</span>
          <strong>{formatPrice(booking.advanceAmount)}</strong>
        </div>

        <div className="booking-detail-item">
          <span>Status</span>
          <div className={`status status-${booking.status.toLowerCase().replaceAll("_", "-")}`}>{booking.status.replaceAll("_", " ")}</div>
        </div>
      </div>
      <div className="booking-actions-card">
        <BookingActions booking={booking} payment={payment}></BookingActions>
      </div>
    </div>
  );
}

export default ManageBooking;
