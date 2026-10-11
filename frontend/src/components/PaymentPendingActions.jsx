import Button from "./Button";
import { useNavigate } from "react-router-dom";

import bookingService from "../services/bookingService";

function PaymentPendingActions({ booking, payment }) {
  const navigate = useNavigate();

  const confirmBooking = async (id) => {
    try {
      const response = await bookingService.confirmBooking(booking.bookingID);
      console.log("Booking confirmed:", response);
      navigate("/bookings");
    } catch (error) {
      console.error("Failed to confirm booking:", error.response?.data || error.message);
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
        <Button onClick={confirmBooking} disabled={payment.paymentStatus != "PROCESSING"}>
          Confirm →
        </Button>
      </div>
    </>
  );
}

export default PaymentPendingActions;
