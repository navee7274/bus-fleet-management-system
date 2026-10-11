import PendingActions from "./PendingActions";
import PaymentPendingActions from "./PaymentPendingActions";
import ConfirmedActions from "./ConfirmedActions";

function BookingActions({ booking, payment }) {
  switch (booking.status) {
    case "PENDING":
      return <PendingActions booking={booking} />;

    case "PAYMENT_PENDING":
      return <PaymentPendingActions booking={booking} payment={payment} />;

    case "CONFIRMED":
      return <ConfirmedActions booking={booking} />;

    default:
      return <p>No actions available</p>;
  }
}

export default BookingActions;
