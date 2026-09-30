import { useState } from "react";
import bookingService from "../../services/bookingService";
import Button from "../../components/Button";
import "../../styles/CustomerBooking.css";

function CustomerBooking() {
  const [form, setForm] = useState({
    customerFirstName: "",
    customerLastName: "",
    customerContactPhone: "",
    customerContactEmail: "",
    customerAddress: "",
    customerCity: "",

    startDateTime: "",
    endDateTime: "",

    startLocation: "",
    destination: "",
    passengerCount: 1,

    busRegistrationNo: "",
  });

  const [availableBuses, setAvailableBuses] = useState([]);

  const [checking, setChecking] = useState(false);
  const [submitting, setSubmitting] = useState(false);

  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  // -----------------------------------------
  // HANDLE INPUT
  // -----------------------------------------

  const handleChange = (e) => {
    const { name, value } = e.target;

    setForm((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  // -----------------------------------------
  // CHECK AVAILABILITY
  // -----------------------------------------

  const checkAvailability = async (e) => {
    e.preventDefault();

    setError("");
    setSuccess("");
    setAvailableBuses([]);

    // Validation
    if (!form.startDateTime || !form.endDateTime) {
      setError("Please select the start and end date/time.");
      return;
    }

    if (new Date(form.startDateTime) >= new Date(form.endDateTime)) {
      setError("End date/time must be after start date/time.");
      return;
    }

    if (Number(form.passengerCount) <= 0) {
      setError("Passenger count must be greater than 0.");
      return;
    }

    try {
      setChecking(true);

      // Matches BusAvailabilityRequest
      const availabilityRequest = {
        startDateTime: form.startDateTime,
        endDateTime: form.endDateTime,
        passengerCount: Number(form.passengerCount),
      };

      console.log("Availability request:", availabilityRequest);

      const response = await bookingService.getAvailableBuses(availabilityRequest);

      console.log("Available buses:", response.data);

      setAvailableBuses(response.data);

      if (!response.data || response.data.length === 0) {
        setError("No buses are available for the selected time.");
      }
    } catch (err) {
      console.error("Availability error:", err);

      setError(err.response?.data || err.response?.data?.message || "Failed to check bus availability.");
    } finally {
      setChecking(false);
    }
  };

  // -----------------------------------------
  // SELECT BUS
  // -----------------------------------------

  const selectBus = (bus) => {
    // Get the registration number from the returned Bus object
    const registrationNo = bus.bRegistrationNo ?? bus.BRegistrationNo ?? bus.busRegistrationNo;

    console.log("Selected bus:", bus);
    console.log("Selected registration:", registrationNo);

    if (!registrationNo) {
      setError("Could not identify the selected bus.");
      return;
    }

    setForm((prev) => ({
      ...prev,
      busRegistrationNo: registrationNo,
    }));

    setError("");
  };

  // -----------------------------------------
  // GET BUS REGISTRATION NUMBER
  // -----------------------------------------

  const getRegistrationNo = (bus) => {
    return bus.bRegistrationNo ?? bus.BRegistrationNo ?? bus.busRegistrationNo;
  };

  // -----------------------------------------
  // ESTIMATED COST
  // -----------------------------------------

  const calculateEstimatedCost = () => {
    if (!form.busRegistrationNo) {
      return 0;
    }

    // Temporary calculation
    const basePrice = 5000;
    const passengerCost = Number(form.passengerCount) * 500;

    return basePrice + passengerCost;
  };

  // -----------------------------------------
  // SUBMIT BOOKING
  // -----------------------------------------

  const handleBooking = async () => {
    setError("");
    setSuccess("");

    if (!form.busRegistrationNo) {
      setError("Please select a bus.");
      return;
    }

    try {
      setSubmitting(true);

      const bookingData = {
        customerFirstName: form.customerFirstName,
        customerLastName: form.customerLastName,
        customerContactPhone: form.customerContactPhone,
        customerContactEmail: form.customerContactEmail,
        customerAddress: form.customerAddress,
        customerCity: form.customerCity,

        startDateTime: form.startDateTime,
        endDateTime: form.endDateTime,

        startLocation: form.startLocation,
        destination: form.destination,

        passengerCount: Number(form.passengerCount),

        busRegistrationNo: form.busRegistrationNo,
      };

      console.log("Sending booking:", bookingData);

      await bookingService.createBooking(bookingData);

      setSuccess("Booking request submitted successfully!");

      setAvailableBuses([]);

      setForm((prev) => ({
        ...prev,
        busRegistrationNo: "",
      }));
    } catch (err) {
      console.error("Booking error:", err);

      setError(err.response?.data || err.response?.data?.message || "Failed to submit booking.");
    } finally {
      setSubmitting(false);
    }
  };

  // -----------------------------------------
  // UI
  // -----------------------------------------

  return (
    <div className="customer-booking-page">
      <div className="booking-container">
        {/* HEADER */}

        <div className="booking-header">
          <h1>Book a Bus</h1>

          <p>Enter your details and journey information.</p>
        </div>

        {/* ERROR */}

        {error && <div className="booking-error">{typeof error === "string" ? error : "Something went wrong."}</div>}

        {/* SUCCESS */}

        {success && <div className="booking-success">{success}</div>}

        {/* CUSTOMER DETAILS */}

        <div className="booking-card">
          <h2>Customer Details</h2>

          <div className="form-row">
            <div className="form-group">
              <label>First Name</label>

              <input type="text" name="customerFirstName" value={form.customerFirstName} onChange={handleChange} required />
            </div>

            <div className="form-group">
              <label>Last Name</label>

              <input type="text" name="customerLastName" value={form.customerLastName} onChange={handleChange} required />
            </div>
          </div>

          <div className="form-row">
            <div className="form-group">
              <label>Phone Number</label>

              <input
                type="tel"
                name="customerContactPhone"
                value={form.customerContactPhone}
                onChange={handleChange}
                maxLength="10"
                placeholder="0712345678"
                required
              />
            </div>

            <div className="form-group">
              <label>Email</label>

              <input type="email" name="customerContactEmail" value={form.customerContactEmail} onChange={handleChange} required />
            </div>
          </div>

          <div className="form-row">
            <div className="form-group">
              <label>Address</label>

              <input type="text" name="customerAddress" value={form.customerAddress} onChange={handleChange} required />
            </div>

            <div className="form-group">
              <label>City</label>

              <input type="text" name="customerCity" value={form.customerCity} onChange={handleChange} required />
            </div>
          </div>
        </div>

        {/* JOURNEY DETAILS */}

        <div className="booking-card">
          <h2>Journey Details</h2>

          <div className="form-row">
            <div className="form-group">
              <label>Start Location</label>

              <input type="text" name="startLocation" value={form.startLocation} onChange={handleChange} placeholder="e.g. Colombo" required />
            </div>

            <div className="form-group">
              <label>Destination</label>

              <input type="text" name="destination" value={form.destination} onChange={handleChange} placeholder="e.g. Kandy" required />
            </div>
          </div>

          <div className="form-row">
            <div className="form-group">
              <label>Start Date & Time</label>

              <input type="datetime-local" name="startDateTime" value={form.startDateTime} onChange={handleChange} required />
            </div>

            <div className="form-group">
              <label>End Date & Time</label>

              <input type="datetime-local" name="endDateTime" value={form.endDateTime} onChange={handleChange} required />
            </div>
          </div>

          <div className="form-group">
            <label>Passenger Count</label>

            <input type="number" name="passengerCount" value={form.passengerCount} onChange={handleChange} min="1" required />
          </div>

          <Button onClick={checkAvailability} disabled={checking}>
            {checking ? "Checking..." : "Check Available Buses"}
          </Button>
        </div>

        {/* AVAILABLE BUSES */}

        {availableBuses.length > 0 && (
          <div className="booking-card">
            <h2>Select a Bus</h2>

            <div className="bus-list">
              {availableBuses.map((bus) => {
                const registrationNo = getRegistrationNo(bus);

                const selected = form.busRegistrationNo === registrationNo;

                return (
                  <div key={registrationNo} className={`bus-option ${selected ? "selected" : ""}`} onClick={() => selectBus(bus)}>
                    <div className="bus-info">
                      <h3>{registrationNo}</h3>

                      <p>Capacity: {bus.capacity}</p>

                      {bus.notes && <p>{bus.notes}</p>}
                    </div>

                    <div className="bus-select">
                      <input type="radio" name="selectedBus" value={registrationNo} checked={selected} onChange={() => selectBus(bus)} />
                    </div>
                  </div>
                );
              })}
            </div>
          </div>
        )}

        {/* SELECTED BUS */}

        {form.busRegistrationNo && (
          <div className="booking-card">
            <h2>Selected Bus</h2>

            <p>Registration Number:</p>

            <strong>{form.busRegistrationNo}</strong>
          </div>
        )}

        {/* PRICE */}

        {form.busRegistrationNo && (
          <div className="booking-card price-card">
            <h2>Estimated Cost</h2>

            <div className="price">Rs. {calculateEstimatedCost().toLocaleString()}</div>

            <p>This is an estimated cost. The final price will be confirmed by the owner.</p>

            <Button onClick={handleBooking} disabled={submitting}>
              {submitting ? "Submitting..." : "Request Booking"}
            </Button>
          </div>
        )}
      </div>
    </div>
  );
}

export default CustomerBooking;
