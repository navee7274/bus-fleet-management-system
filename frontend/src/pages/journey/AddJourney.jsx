import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Button from "../../components/Button";
import journeyService from "../../services/journeyService";
import "../../styles/Journey.css";

function AddJourney() {
  const navigate = useNavigate();

  const isEdit = false;

  const [formData, setFormData] = useState({
    journeyID: "",
    journeyDate: "",
    busRegistrationNo: "",
    driverID: "",
    purpose: "",
    clientDestination: "",
    startOdometer: "",
    endOdometer: "",
    kmTraveled: "",
    incomeAmount: "",
    notes: "",
  });

  const [error, setError] = useState("");
  const [saving, setSaving] = useState(false);

  const handleChange = (event) => {
    const { name, value, type, checked } = event.target;

    setFormData((current) => ({
      ...current,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      setSaving(true);
      setError("");

      await journeyService.create({
        journeyID: data.journeyID || "",
        journeyDate: data.journeyDate || "",
        busRegistrationNo: data.bus?.busRegistrationNo || "",
        driverID: data.driver?.DriverID || "",
        purpose: data.purpose || "",
        clientDestination: data.clientDestination || "",
        startOdometer: data.startOdometer ?? "",
        endOdometer: data.endOdometer ?? "",
        kmTraveled: data.kmTraveled ?? "",
        incomeAmount: data.incomeAmount ?? "",
        notes: data.notes || "",
      });

      navigate("/journeys");
    } catch (error) {
      console.error(error);
      setError(error.response?.data?.message || "Failed to add journey.");
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="journey-page">
      <div className="page-header">
        <div>
          <h1>Add a Journey</h1>
          <p>Enter your journey details below.</p>
        </div>
      </div>

      <form className="journey-form" onSubmit={handleSubmit}>
        {/* Error */}
        {error && <div className="error-message">{error}</div>}
        {/* Journey ID */}
        <div className="form-group">
          <label>Journey ID</label>

          <input type="number" name="journeyID" value={formData.journeyID} onChange={handleChange} disabled={isEdit} required />
        </div>

        {/* Date + Bus */}
        <div className="form-row">
          <div className="form-group">
            <label>Journey Date</label>

            <input type="date" name="journeyDate" value={formData.journeyDate} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label>Bus Registration No</label>

            <input type="text" name="busRegistrationNo" value={formData.busRegistrationNo} onChange={handleChange} placeholder="ND-4521" required />
          </div>
        </div>

        {/* Driver + Purpose */}
        <div className="form-row">
          <div className="form-group">
            <label>Driver ID</label>

            <input type="text" name="driverID" value={formData.driverID} onChange={handleChange} placeholder="DRV001" required />
          </div>

          <div className="form-group">
            <label>Purpose</label>

            <input type="text" name="purpose" value={formData.purpose} onChange={handleChange} maxLength={100} placeholder="Hire" />
          </div>
        </div>

        {/* Destination */}
        <div className="form-group">
          <label>Client Destination</label>

          <input
            type="text"
            name="clientDestination"
            value={formData.clientDestination}
            onChange={handleChange}
            maxLength={20}
            placeholder="Kandy"
            required
          />
        </div>

        {/* Odometer */}
        <div className="form-row">
          <div className="form-group">
            <label>Start Odometer</label>

            <input type="number" step="0.01" min="0" name="startOdometer" value={formData.startOdometer} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label>End Odometer</label>

            <input type="number" step="0.01" min="0" name="endOdometer" value={formData.endOdometer} onChange={handleChange} required />
          </div>
        </div>

        {/* KM + Income */}
        <div className="form-row">
          <div className="form-group">
            <label>KM Travelled</label>

            <input type="number" step="0.01" name="kmTraveled" value={formData.kmTraveled} readOnly />

            <small>Automatically calculated from odometer readings</small>
          </div>

          <div className="form-group">
            <label>Income Amount</label>

            <input
              type="number"
              step="0.01"
              min="0"
              name="incomeAmount"
              value={formData.incomeAmount}
              onChange={handleChange}
              placeholder="45000.00"
              required
            />
          </div>
        </div>

        {/* Notes */}
        <div className="form-group">
          <label>Notes</label>

          <textarea name="notes" value={formData.notes} onChange={handleChange} maxLength={100} rows="4" placeholder="School trip hire" />
        </div>

        {/* Buttons */}
        <div className="form-actions">
          <Button type="button" onClick={() => navigate("/journeys")}>
            Cancel
          </Button>

          <Button type="submit" disabled={saving}>
            {saving ? "Saving..." : isEdit ? "Update Journey" : "Create Journey"}
          </Button>
        </div>
      </form>
    </div>
  );
}

export default AddJourney;
