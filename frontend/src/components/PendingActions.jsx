import { useEffect, useState } from "react";
import Button from "./Button";
import bookingService from "../services/bookingService";
import { useNavigate } from "react-router-dom";

function PendingActions({ booking }) {
  const [drivers, setDrivers] = useState([]);
  const [finalPrice, setFinalPrice] = useState(booking.finalPrice ?? "");
  const [advanceAmount, setAdvanceAmount] = useState(booking.advanceAmount ?? "");
  const [paymentMethod, setPaymentMethod] = useState("");
  const [driverId, setDriverId] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    let cancelled = false;

    const fetchDrivers = async () => {
      try {
        const availabilityRequest = {
          startDateTime: booking.startDateTime,
          endDateTime: booking.endDateTime,
          passengerCount: booking.passengerCount,
        };

        const response = await bookingService.getAvailableDrivers(availabilityRequest);

        const result = response.data;
        const availableDrivers = Array.isArray(result) ? result : (result.drivers ?? []);

        if (!cancelled) {
          setDrivers(availableDrivers);
        }
      } catch (err) {
        if (!cancelled) {
          console.error(err);
          setError("Failed to load available drivers.");
        }
      }
    };

    fetchDrivers();

    return () => {
      cancelled = true;
    };
  }, [booking.startDateTime, booking.endDateTime, booking.passengerCount]);

  const approveBooking = async (event) => {
    event.preventDefault();
    setError("");

    const price = Number(finalPrice);
    const advance = Number(advanceAmount);

    if (finalPrice === "" || advanceAmount === "" || price <= 0 || advance <= 0 || advance > price || !driverId) {
      setError("Enter valid prices and select a driver.");
      return;
    }

    try {
      setLoading(true);
      await bookingService.approveBooking(booking.bookingID, paymentMethod, String(driverId), Number(advance), Number(price));
      navigate("/bookings");
    } catch (err) {
      console.error(err);
      setError(err.response?.data?.message || "Failed to approve booking.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={approveBooking}>
      <div className="pending-inputs-container">
        <div className="form-group">
          <label htmlFor="finalPrice">Final Price</label>
          <input id="finalPrice" type="number" min="1" value={finalPrice} onChange={(e) => setFinalPrice(e.target.value)} required />
        </div>

        <div className="form-group">
          <label htmlFor="advanceAmount">Advance Amount</label>
          <input id="advanceAmount" type="number" min="1" value={advanceAmount} onChange={(e) => setAdvanceAmount(e.target.value)} required />
        </div>

        <div className="form-group">
          <label htmlFor="driverId">Assign Driver</label>
          <select id="driverId" value={driverId} onChange={(e) => setDriverId(e.target.value)} required>
            <option value="" disabled>
              Select a driver
            </option>

            {drivers.map((driver) => (
              <option key={driver.driverID ?? driver.DriverID} value={driver.driverID ?? driver.DriverID}>
                {driver.Name}
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label htmlFor="paymentMethod">Payment Method</label>
          <select id="paymentMethod" value={paymentMethod} onChange={(e) => setPaymentMethod(e.target.value)} required>
            <option value="" disabled>
              Select a payment method
            </option>
            <option value="Bank_Transfer">Bank Transfer</option>
            <option value="Card_Payment">Card Payment</option>
            <option value="Cash">Cash</option>
          </select>
        </div>
      </div>

      {error && <p className="error-message">{error}</p>}

      <div className="button-row">
        <Button type="submit" disabled={loading}>
          {loading ? "Approving..." : "Approve"}
        </Button>
      </div>
    </form>
  );
}

export default PendingActions;
