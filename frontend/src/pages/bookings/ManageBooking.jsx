import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import bookingService from "../../services/bookingService";
import Loading from "../../components/Loading";
import Button from "../../components/Button";
import "../../styles/Booking.css";

function ManageBooking() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [booking, setBooking] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadBooking = async () => {
      try {
        setLoading(true);

        const data = await bookingService.getById(id);
        setBooking(data);
      } catch (err) {
        console.error(err);
        setError("Failed to load booking.");
      } finally {
        setLoading(false);
      }
    };

    loadBooking();
  }, [id]);

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

  return (
    <div className="booking-manage-page">
      <div className="booking-manage-header">
        <div>
          <h1>Booking #{booking.bookingID.toString().padStart(8, "0")}</h1>
          <p>Manage booking</p>
        </div>

        <div className="booking-header-actions">
          <Button onClick={() => navigate(`/bookings/${booking.bookingID}/edit`)}>Edit</Button>
          <Button onClick={() => navigate("/bookings")}>Back</Button>
        </div>
      </div>
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
      <div className="booking-details-card">
        <div className="booking-detail-item">
          <span>Start</span>
          <strong>{booking.startLocation}</strong>
        </div>

        <div className="booking-detail-item">
          <span>Destination</span>
          <strong>{booking.destination}</strong>
        </div>
      </div>
      <div className="booking-details-card">
        <div className="booking-detail-item">
          <span>Start</span>
          <strong>
            {formatDate(booking.startDateTime)} • {formatTime(booking.endDateTime)}
          </strong>
        </div>

        <div className="booking-detail-item">
          <span>Destination</span>
          <strong>{formatDate(booking.endDateTime)}</strong>
        </div>
      </div>
    </div>
  );
}

export default ManageBooking;
