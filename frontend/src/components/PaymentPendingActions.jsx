import Button from "./Button";
import Modal from "./Modal";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

import bookingService from "../services/bookingService";

function PaymentPendingActions({ booking, payment }) {
  const navigate = useNavigate();

  const [loading, setLoading] = useState(false);
  const [showCancelModal, setShowCancelModal] = useState(false);

  const confirmBooking = async () => {
    try {
      setLoading(true);

      const response = await bookingService.confirmBooking(booking.bookingID);

      console.log("Booking confirmed:", response);
      navigate("/bookings");
    } catch (error) {
      console.error("Failed to confirm booking:", error.response?.data || error.message);

      alert(error.response?.data?.message || "Failed to confirm booking. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  const cancelBooking = async () => {
    try {
      setLoading(true);

      await bookingService.cancelBooking(booking.bookingID);

      navigate("/bookings");
    } catch (error) {
      console.error("Failed to cancel booking:", error.response?.data || error.message);

      alert(error.response?.data?.message || "Failed to cancel booking. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <div className="booking-details-card grid-four">
        <div className="booking-detail-item">
          <span>Customer Contact</span>
          <strong>{booking.customerContactPhone}</strong>
        </div>

        <div className="booking-detail-item">
          <span>Advance Amount</span>
          <strong>{booking.advanceAmount}</strong>
        </div>

        <div className="booking-detail-item">
          <span>Payment Method</span>
          <strong>{payment.paymentMethod}</strong>
        </div>

        <div className="booking-detail-item">
          <span>Payment Status</span>
          <strong
            style={{
              color: payment.paymentStatus === "PROCESSING" ? "green" : "red",
            }}
          >
            {payment.paymentStatus === "PROCESSING" ? "Done" : "Not Done"}
          </strong>
        </div>
      </div>

      <div className="button-row">
        <Button onClick={() => setShowCancelModal(true)} disabled={loading}>
          Cancel Booking
        </Button>

        <Button onClick={confirmBooking} disabled={loading || payment.paymentStatus !== "PROCESSING"}>
          {loading ? "Processing..." : "Confirm →"}
        </Button>
      </div>

      <Modal
        isOpen={showCancelModal}
        onClose={() => {
          if (!loading) {
            setShowCancelModal(false);
          }
        }}
        title="Cancel Booking"
      >
        <p>
          Are you sure you want to cancel booking <strong>#{booking.bookingID}</strong>?
        </p>

        <p>This action cannot be undone. Payments are non-refundable.</p>

        <div className="modal-actions">
          <Button className="btn-secondary" onClick={() => setShowCancelModal(false)} disabled={loading}>
            Keep Booking
          </Button>

          <Button className="btn-danger" onClick={cancelBooking} disabled={loading}>
            {loading ? "Cancelling..." : "Confirm Cancellation"}
          </Button>
        </div>
      </Modal>
    </>
  );
}

export default PaymentPendingActions;
