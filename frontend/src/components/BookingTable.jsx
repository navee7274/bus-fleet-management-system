import { useNavigate } from "react-router-dom";

function BookingTable({ bookings, emptyStateMessage }) {
  const navigate = useNavigate();

  return (
    <div className="booking-table-container">
      {bookings.length === 0 ? (
        <div className="empty-state">
          <p>{emptyStateMessage}</p>
        </div>
      ) : (
        <table className="booking-table">
          <thead>
            <tr>
              <th>Booking ID</th>
              <th>Customer</th>
              <th>Contact No.</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>
            {bookings.map((booking) => (
              <tr key={booking.bookingID}>
                <td>
                  <strong>{booking.bookingID.toString().padStart(8, "0")}</strong>
                </td>

                <td>{booking.customerFirstName + " " + booking.customerLastName}</td>

                <td>{booking.customerContactPhone}</td>

                <td>
                  <span className={`status status-${booking.status.toLowerCase().replaceAll("_", "-")}`}>{booking.status.replaceAll("_", " ")}</span>
                </td>

                <td>
                  <div className="action-buttons">
                    <button className="action-manage" onClick={() => navigate(`/bookings/${booking.bookingID}/manage`)}>
                      Manage
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default BookingTable;
