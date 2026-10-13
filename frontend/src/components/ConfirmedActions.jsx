import { useState } from "react";
import { useNavigate } from "react-router-dom";

import Button from "./Button";
import Modal from "./Modal";
import bookingService from "../services/bookingService";

function ConfirmedActions({ booking }) {
  const navigate = useNavigate();

  const [startOdo, setStartOdo] = useState("");
  const [endOdo, setEndOdo] = useState("");
  const [loading, setLoading] = useState(false);
  const [showCancelModal, setShowCancelModal] = useState(false);

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

  const completeBooking = async (e) => {
    e.preventDefault();

    if (Number(endOdo) <= Number(startOdo)) {
      alert("End odometer must be greater than start odometer.");
      return;
    }

    try {
      setLoading(true);

      const response = await bookingService.completeBooking(booking.bookingID, startOdo, endOdo);

      console.log("Booking completed:", response);

      navigate("/bookings");
    } catch (error) {
      console.error("Failed to complete booking:", error.response?.data || error.message);

      alert(error.response?.data?.message || "Failed to complete booking. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <form onSubmit={completeBooking}>
        <div className="pending-inputs-container">
          <div className="form-group">
            <label htmlFor="startOdo">Start Odometer</label>
            <input id="startOdo" type="number" min="0" step="0.01" value={startOdo} onChange={(e) => setStartOdo(e.target.value)} required />
          </div>

          <div className="form-group">
            <label htmlFor="endOdo">End Odometer</label>
            <input id="endOdo" type="number" min="0" step="0.01" value={endOdo} onChange={(e) => setEndOdo(e.target.value)} required />
          </div>
        </div>

        <div className="button-row">
          <Button type="button" onClick={() => setShowCancelModal(true)} disabled={loading}>
            Cancel Booking
          </Button>

          <Button type="submit" disabled={loading}>
            {loading ? "Processing..." : "Complete Booking"}
          </Button>
        </div>
      </form>

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

export default ConfirmedActions;
