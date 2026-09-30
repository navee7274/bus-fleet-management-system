import { useState } from "react";
import api from "../../services/api";
import "../../styles/CreateBooking.css";

function CreateBooking() {
  const [formData, setFormData] = useState({
    customerFirstName: "",
    customerLastName: "",
    customerContactPhone: "",
    customerContactEmail: "",
    startDateTime: "",
    endDateTime: "",
    startLocation: "",
    endLocation: "",
    passengerCount: 1,
  });

  const [availableBuses, setAvailableBuses] = useState([]);
  const [selectedBus, setSelectedBus] = useState(null);
  const [loading, setLoading] = useState(false);
  const [searched, setSearched] = useState(false);
  const [error, setError] = useState("");

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value,
    }));
  };

  const checkAvailability = async (event) => {
    event.preventDefault();

    setError("");
    setSearched(false);
    setSelectedBus(null);
    setLoading(true);

    try {
      const response = await api.get("/buses/availability", {
        params: {
          startDateTime: formData.startDateTime,
          endDateTime: formData.endDateTime,
          passengerCount: formData.passengerCount,
        },
      });

      setAvailableBuses(response.data);
      setSearched(true);
    } catch (error) {
      console.error(error);
      setError("Unable to check bus availability.");
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!selectedBus) {
      setError("Please select a bus first.");
      return;
    }

    setLoading(true);
    setError("");

    try {
      const bookingData = {
        ...formData,
        passengerCount: Number(formData.passengerCount),
        busID: selectedBus.busID,
      };

      const response = await api.post("/bookings", bookingData);

      console.log("Booking created:", response.data);

      alert("Booking request submitted successfully. " + "Our team will contact you shortly.");
    } catch (error) {
      console.error(error);
      setError("Unable to create the booking.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="booking-page">
      <div className="booking-container">
        {/* Header */}
        <div className="booking-header">
          <h1>Book a Bus</h1>

          <p>Tell us about your journey and find an available bus.</p>
        </div>

        {/* Progress */}
        <div className="booking-progress">
          <div className="progress-step active">
            <span>1</span>
            <p>Journey Details</p>
          </div>

          <div className="progress-line"></div>

          <div className={`progress-step ${selectedBus ? "active" : ""}`}>
            <span>2</span>
            <p>Select Bus</p>
          </div>

          <div className="progress-line"></div>

          <div className="progress-step">
            <span>3</span>
            <p>Booking</p>
          </div>
        </div>

        <form onSubmit={handleSubmit}>
          {/* Customer Details */}
          <section className="booking-card">
            <div className="section-title">
              <h2>Customer Details</h2>
              <p>How can we contact you?</p>
            </div>

            <div className="form-grid">
              <div className="form-group">
                <label>First Name</label>

                <input
                  type="text"
                  name="customerFirstName"
                  value={formData.customerFirstName}
                  onChange={handleChange}
                  placeholder="Enter first name"
                  required
                />
              </div>

              <div className="form-group">
                <label>Last Name</label>

                <input
                  type="text"
                  name="customerLastName"
                  value={formData.customerLastName}
                  onChange={handleChange}
                  placeholder="Enter last name"
                  required
                />
              </div>

              <div className="form-group">
                <label>Phone Number</label>

                <input
                  type="tel"
                  name="customerContactPhone"
                  value={formData.customerContactPhone}
                  onChange={handleChange}
                  placeholder="07XXXXXXXX"
                  required
                />
              </div>

              <div className="form-group">
                <label>Email Address</label>

                <input
                  type="email"
                  name="customerContactEmail"
                  value={formData.customerContactEmail}
                  onChange={handleChange}
                  placeholder="example@email.com"
                  required
                />
              </div>
            </div>
          </section>

          {/* Journey Details */}
          <section className="booking-card">
            <div className="section-title">
              <h2>Journey Details</h2>
              <p>Where and when are you travelling?</p>
            </div>

            <div className="form-grid">
              <div className="form-group">
                <label>Starting Location</label>

                <input type="text" name="startLocation" value={formData.startLocation} onChange={handleChange} placeholder="e.g. Colombo" required />
              </div>

              <div className="form-group">
                <label>Destination</label>

                <input type="text" name="endLocation" value={formData.endLocation} onChange={handleChange} placeholder="e.g. Kandy" required />
              </div>

              <div className="form-group">
                <label>Departure</label>

                <input type="datetime-local" name="startDateTime" value={formData.startDateTime} onChange={handleChange} required />
              </div>

              <div className="form-group">
                <label>Return / End Date</label>

                <input type="datetime-local" name="endDateTime" value={formData.endDateTime} onChange={handleChange} required />
              </div>

              <div className="form-group">
                <label>Number of Passengers</label>

                <input type="number" name="passengerCount" value={formData.passengerCount} onChange={handleChange} min="1" required />
              </div>
            </div>

            <button type="button" className="availability-button" onClick={checkAvailability} disabled={loading}>
              {loading ? "Checking..." : "Check Bus Availability"}
            </button>
          </section>

          {/* Available Buses */}
          {searched && (
            <section className="booking-card">
              <div className="section-title">
                <h2>Available Buses</h2>
                <p>Select a bus that suits your journey.</p>
              </div>

              {availableBuses.length === 0 ? (
                <div className="no-buses">
                  <h3>No buses available</h3>
                  <p>Try changing your journey date or time.</p>
                </div>
              ) : (
                <div className="bus-list">
                  {availableBuses.map((bus) => (
                    <div
                      key={bus.busID}
                      className={`bus-option ${selectedBus?.busID === bus.busID ? "selected" : ""}`}
                      onClick={() => setSelectedBus(bus)}
                    >
                      <div className="bus-info">
                        <div className="bus-icon">🚌</div>

                        <div>
                          <h3>{bus.registrationNumber || bus.busNumber}</h3>

                          <p>{bus.busType || "Passenger Bus"}</p>
                        </div>
                      </div>

                      <div className="bus-capacity">
                        <span>Capacity</span>
                        <strong>{bus.capacity} passengers</strong>
                      </div>

                      <div className="bus-price">
                        <span>Estimated Cost</span>
                        <strong>Rs. {bus.approximateCost ? Number(bus.approximateCost).toLocaleString() : "Contact us"}</strong>
                      </div>

                      <div className="bus-select">
                        <input type="radio" name="selectedBus" checked={selectedBus?.busID === bus.busID} onChange={() => setSelectedBus(bus)} />
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </section>
          )}

          {/* Submit */}
          {selectedBus && (
            <section className="booking-summary">
              <div>
                <span>Selected Bus</span>

                <strong>{selectedBus.registrationNumber || selectedBus.busNumber}</strong>
              </div>

              <div>
                <span>Route</span>

                <strong>
                  {formData.startLocation} → {formData.endLocation}
                </strong>
              </div>

              <button type="submit" className="booking-submit" disabled={loading}>
                {loading ? "Submitting..." : "Request Booking"}
              </button>
            </section>
          )}

          {error && <div className="booking-error">{error}</div>}
        </form>

        {/* Information */}
        <div className="booking-note">
          <strong>How booking works</strong>

          <p>
            Your booking request is temporary until our team contacts you and confirms the final price. You will then be able to make the advance
            payment.
          </p>
        </div>
      </div>
    </div>
  );
}

export default CreateBooking;
