import { useState } from "react";
import { useNavigate } from "react-router-dom";
import bookingService from "../../services/bookingService";
import Button from "../../components/Button";
import "../../styles/CustomerBooking.css";

import "../../styles/LandingPage.css";

import Navbar from "../../components/CustomerNavBar";
import Footer from "../../components/CustomerFooter";

function CustomerBooking() {
  const navigate = useNavigate();

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
  const [bookingNumber, setBookingNumber] = useState("");

  const [step, setStep] = useState(1);

  // -----------------------------------------
  // FORM NAVIGATION
  // -----------------------------------------

  const nextStep = () => {
    setError("");

    if (step === 1) {
      const requiredFields = [
        ["customerFirstName", "First name"],
        ["customerLastName", "Last name"],
        ["customerContactPhone", "Phone number"],
        ["customerContactEmail", "Email"],
        ["customerAddress", "Address"],
        ["customerCity", "City"],
      ];

      for (const [field, label] of requiredFields) {
        if (!form[field].trim()) {
          setError(`${label} is required.`);
          return;
        }
      }

      // Sri Lankan mobile number validation
      if (!/^0\d{9}$/.test(form.customerContactPhone)) {
        setError("Enter a valid 10-digit phone number.");
        return;
      }

      if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.customerContactEmail)) {
        setError("Enter a valid email address.");
        return;
      }
    }

    if (step === 2 && !form.busRegistrationNo) {
      setError("Please check availability and select a bus.");
      return;
    }

    if (step === 3 && !form.busRegistrationNo) {
      setError("Please select a bus before continuing.");
      return;
    }

    setStep((prev) => Math.min(prev + 1, 4));
  };

  const prevStep = () => {
    setError("");
    setStep((prev) => Math.max(prev - 1, 1));
  };

  const handleCancel = () => {
    navigate("/");
  };

  const handleDone = () => {
    navigate("/");
  };

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

    if (!form.startLocation.trim() || !form.destination.trim()) {
      setError("Start location and destination are required.");
      return;
    }

    if (form.startLocation.trim().toLowerCase() === form.destination.trim().toLowerCase()) {
      setError("Start location and destination cannot be the same.");
      return;
    }

    if (!form.startDateTime || !form.endDateTime) {
      setError("Please select both start and end date/time.");
      return;
    }

    const start = new Date(form.startDateTime);
    const end = new Date(form.endDateTime);

    if (Number.isNaN(start.getTime()) || Number.isNaN(end.getTime())) {
      setError("Please enter valid dates and times.");
      return;
    }

    if (start <= new Date()) {
      setError("The journey start time must be in the future.");
      return;
    }

    if (end <= start) {
      setError("End date/time must be after start date/time.");
      return;
    }

    const passengers = Number(form.passengerCount);

    if (!Number.isInteger(passengers) || passengers < 1) {
      setError("Passenger count must be a positive whole number.");
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
    setBookingNumber("");

    const requiredFields = [
      ["customerFirstName", "First name"],
      ["customerLastName", "Last name"],
      ["customerContactPhone", "Phone number"],
      ["customerContactEmail", "Email"],
      ["customerAddress", "Address"],
      ["customerCity", "City"],
      ["startLocation", "Start location"],
      ["destination", "Destination"],
      ["startDateTime", "Start date/time"],
      ["endDateTime", "End date/time"],
      ["busRegistrationNo", "Bus selection"],
    ];

    for (const [field, label] of requiredFields) {
      if (!String(form[field] ?? "").trim()) {
        setError(`${label} is required.`);
        return;
      }
    }

    if (!/^0\d{9}$/.test(form.customerContactPhone)) {
      setError("Enter a valid 10-digit phone number.");
      return;
    }

    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.customerContactEmail)) {
      setError("Enter a valid email address.");
      return;
    }

    if (new Date(form.startDateTime) <= new Date() || new Date(form.endDateTime) <= new Date(form.startDateTime)) {
      setError("Please check your journey date and time.");
      return;
    }

    if (!Number.isInteger(Number(form.passengerCount)) || Number(form.passengerCount) < 1) {
      setError("Enter a valid passenger count.");
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

      const response = await bookingService.createBooking(bookingData);
      const bookingResponse = response?.data?.booking ?? response?.data?.data ?? response?.data;
      const returnedBookingNumber = bookingResponse?.bookingID;

      setSuccess("Booking request submitted successfully!");
      if (returnedBookingNumber != null) {
        setBookingNumber(String(returnedBookingNumber));
      }

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
      <Navbar />
      <div className="booking-container">
        {/* HEADER */}

        <div className="booking-header">
          <div className="booking-header-text">
            <h1>Book a Bus</h1>
            <p>Enter your details and journey information.</p>
          </div>
          {/* BOOKING PROGRESS */}

          <p>Step {step} of 4</p>
        </div>

        <div className="booking-progress">
          <div className="booking-progress-fill" style={{ width: `${(step / 4) * 100}%` }} />
        </div>

        {/* ERROR */}

        {error && <div className="booking-error">{typeof error === "string" ? error : "Something went wrong."}</div>}

        {/* SUCCESS */}

        {success && <div className="booking-success">{success}</div>}

        {/* CUSTOMER DETAILS */}

        {step === 1 && (
          <div className="booking-card">
            <h2>Customer Details</h2>
            <div className="form-row">
              <div className="form-group">
                <label>First Name</label>

                <input type="text" name="customerFirstName" value={form.customerFirstName} onChange={handleChange} required maxLength={50} />
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
                  placeholder="0712345678"
                  pattern="0[0-9]{9}"
                  maxLength={10}
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
            <div className="button-row">
              <Button onClick={handleCancel} className="secondary-button">
                Cancel
              </Button>
              <Button onClick={nextStep}>Next</Button>
            </div>
          </div>
        )}

        {/* JOURNEY DETAILS */}

        {step === 2 && (
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

              <input type="number" name="passengerCount" value={form.passengerCount} onChange={handleChange} min="1" step="1" required />
            </div>

            <div className="button-row">
              <Button onClick={prevStep} className="secondary-button">
                Back
              </Button>
              <Button onClick={checkAvailability} disabled={checking}>
                {checking ? "Checking..." : "Check Availability"}
              </Button>
            </div>
          </div>
        )}

        {/* AVAILABLE BUSES */}

        {step === 2 && availableBuses.length > 0 && (
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
              <div className="button-row">
                <Button onClick={nextStep}>Next</Button>
              </div>
            </div>
          </div>
        )}

        {/* SELECTED BUS */}

        {step === 3 && form.busRegistrationNo && (
          <div className="booking-card">
            <h2>Selected Bus</h2>
            <div className="booking-bus-info">
              <p>Registration Number:</p>
              <strong>{form.busRegistrationNo}</strong>
            </div>
            <div className="button-row">
              <Button onClick={prevStep} className="secondary-button">
                Back
              </Button>
              <Button onClick={nextStep}>Next</Button>
            </div>
          </div>
        )}

        {/* PRICE */}

        {step === 4 && form.busRegistrationNo && (
          <div className="booking-card price-card">
            <h2>Estimated Cost</h2>

            <div className="price">Rs. {calculateEstimatedCost().toLocaleString()}</div>

            <p>This is an estimated cost. The final price will be confirmed by the owner.</p>
            <div className="button-row">
              <Button onClick={prevStep} className="secondary-button">
                Back
              </Button>
              <Button onClick={handleBooking} disabled={submitting}>
                {submitting ? "Submitting..." : "Request Booking"}
              </Button>
            </div>
          </div>
        )}

        {step === 4 && success && (
          <div className="booking-card price-card">
            <h2>Booking Sent</h2>

            <p>Booking number: Please remember the booking number for future uses.</p>
            <div className="price">{bookingNumber && <p>#{bookingNumber}</p>}</div>

            <p>It may take up to 12 hours for the owner to review the request. Contact the owner for faster process</p>
            <div className="button-row">
              <Button onClick={handleDone}>Done</Button>
            </div>
          </div>
        )}
      </div>
      <Footer />
    </div>
  );
}

export default CustomerBooking;
