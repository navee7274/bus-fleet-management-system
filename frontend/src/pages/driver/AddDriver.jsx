import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Button from "../../components/Button";
import driverService from "../../services/driverService";
import "../../styles/Driver.css";

function AddDriver() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    Name: "",
    NIC: "",
    ContactNo: "",
    Notes: "",
    BaseMonthlySalary: "",
    PerTripAllowence: "",
    DActive: true,
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

      await driverService.createDriver({
        Name: formData.Name,
        NIC: formData.NIC,
        ContactNo: formData.ContactNo,
        Notes: formData.Notes,
        BaseMonthlySalary: Number(formData.BaseMonthlySalary),
        PerTripAllowence: Number(formData.PerTripAllowence),
        DActive: formData.DActive,
      });

      navigate("/drivers");
    } catch (error) {
      console.error(error);
      setError(error.response?.data?.message || "Failed to add driver.");
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="driver-page">
      <div className="page-header">
        <div>
          <h1>Add Driver</h1>
          <p>Add a new driver here.</p>
        </div>
      </div>

      <form className="driver-form" onSubmit={handleSubmit}>
        {error && <div className="error-message">{error}</div>}

        {/* Name & NIC */}
        <div className="form-row">
          <div className="form-group">
            <label>Name</label>

            <input type="text" name="Name" value={formData.Name} onChange={handleChange} placeholder="Driver's full name" maxLength="100" required />
          </div>

          <div className="form-group">
            <label>NIC</label>

            <input type="text" name="NIC" value={formData.NIC} onChange={handleChange} placeholder="NIC number" maxLength="20" required />
          </div>
        </div>

        {/* Contact & Monthly Salary */}
        <div className="form-row">
          <div className="form-group">
            <label>Contact Number</label>

            <input type="text" name="ContactNo" value={formData.ContactNo} onChange={handleChange} placeholder="07XXXXXXXX" maxLength="15" required />
          </div>

          <div className="form-group">
            <label>Base Monthly Salary</label>

            <input
              type="number"
              name="BaseMonthlySalary"
              value={formData.BaseMonthlySalary}
              onChange={handleChange}
              min="0"
              step="0.01"
              placeholder="0.00"
              required
            />
          </div>
        </div>

        {/* Per Trip Allowance */}
        <div className="form-group">
          <label>Per Trip Allowance</label>

          <input
            type="number"
            name="PerTripAllowence"
            value={formData.PerTripAllowence}
            onChange={handleChange}
            min="0"
            step="0.01"
            placeholder="0.00"
            required
          />
        </div>

        {/* Notes */}
        <div className="form-group">
          <label>Notes</label>

          <textarea
            name="Notes"
            value={formData.Notes}
            onChange={handleChange}
            maxLength="225"
            rows="4"
            placeholder="Additional information about the driver..."
          />
        </div>

        {/* Active */}
        <div className="checkbox-group">
          <input type="checkbox" id="DActive" name="DActive" checked={formData.DActive} onChange={handleChange} />

          <label htmlFor="DActive">Driver is active</label>
        </div>

        {/* Actions */}
        <div className="form-actions">
          <Button type="button" className="btn-secondary" onClick={() => navigate("/drivers")}>
            Cancel
          </Button>

          <Button type="submit" disabled={saving}>
            {saving ? "Saving..." : "Add Driver"}
          </Button>
        </div>
      </form>
    </div>
  );
}

export default AddDriver;
