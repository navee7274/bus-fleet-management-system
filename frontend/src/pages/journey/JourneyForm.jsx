import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import journeyService from "../../services/journeyService";
import Button from "../../components/Button";
import Loading from "../../components/Loading";
import "../../styles/Journey.css";

function JourneyForm() {
  const navigate = useNavigate();
  const { id } = useParams();

  const isEdit = Boolean(id);

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

  const [loading, setLoading] = useState(false);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  /*
   * Load journey when editing
   */
  useEffect(() => {
    if (!isEdit) {
      return;
    }

    const loadJourney = async () => {
      try {
        setLoading(true);
        setError("");

        const data = await journeyService.getById(id);

        setFormData({
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
      } catch (err) {
        console.error(err);
        setError("Failed to load journey.");
      } finally {
        setLoading(false);
      }
    };

    loadJourney();
  }, [id, isEdit]);

  /*
   * Handle input changes
   */
  const handleChange = (e) => {
    const { name, value } = e.target;

    setFormData((prev) => {
      const updated = {
        ...prev,
        [name]: value,
      };

      /*
       * Automatically calculate KM travelled
       */
      if (name === "startOdometer" || name === "endOdometer") {
        const start = Number(name === "startOdometer" ? value : prev.startOdometer);

        const end = Number(name === "endOdometer" ? value : prev.endOdometer);

        if (!isNaN(start) && !isNaN(end) && end >= start) {
          updated.kmTraveled = (end - start).toFixed(2);
        } else {
          updated.kmTraveled = "";
        }
      }

      return updated;
    });
  };

  /*
   * Submit form
   */
  const handleSubmit = async (e) => {
    e.preventDefault();

    setError("");

    /*
     * Basic validation
     */
    if (Number(formData.endOdometer) < Number(formData.startOdometer)) {
      setError("End odometer cannot be less than start odometer.");
      return;
    }

    try {
      setSaving(true);

      /*
       * This is the request body sent to Spring Boot.
       */
      const journeyData = {
        journeyID: Number(formData.journeyID),

        journeyDate: formData.journeyDate,

        busRegistrationNo: formData.busRegistrationNo,

        driverID: formData.driverID,

        purpose: formData.purpose,

        clientDestination: formData.clientDestination,

        startOdometer: Number(formData.startOdometer),

        endOdometer: Number(formData.endOdometer),

        kmTraveled: Number(formData.kmTraveled),

        incomeAmount: Number(formData.incomeAmount),

        notes: formData.notes,
      };

      if (isEdit) {
        await journeyService.update(id, journeyData);
      } else {
        await journeyService.create(journeyData);
      }

      navigate("/journeys");
    } catch (err) {
      console.error(err);

      setError(err.response?.data?.message || "Failed to save journey.");
    } finally {
      setSaving(false);
    }
  };

  if (loading) {
    return <Loading />;
  }

  return (
    <div className="journey-form-page">
      <div className="journey-form-container">
        {/* Header */}
        <div className="journey-form-header">
          <h1>{isEdit ? "Edit Journey" : "Add Journey"}</h1>

          <p>{isEdit ? "Update journey information" : "Enter journey details"}</p>
        </div>

        {/* Error */}
        {error && <div className="error-message">{error}</div>}

        <form onSubmit={handleSubmit}>
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
    </div>
  );
}

export default JourneyForm;
