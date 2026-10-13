import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Button from "../../components/Button";
import maintenanceLogService from "../../services/maintenanceLogService";
import "../../styles/Maintenance.css";

function AddMaintenance() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    MaintenanceDate: "",
    busRegistrationNo: "",
    MType: "",
    Description: "",
    Cost: "",
    Odometer: "",
    NextServiceDue: "",
  });

  const [error, setError] = useState("");
  const [saving, setSaving] = useState(false);

  const maintenanceTypes = ["Service", "Repair"];

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((current) => ({
      ...current,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      setSaving(true);
      setError("");

      const maintenanceData = {
        maintenanceDate: formData.MaintenanceDate,
        busRegistrationNo: formData.busRegistrationNo.trim(),
        MType: formData.MType,
        description: formData.Description.trim(),
        cost: Number(formData.Cost),
        odometer: Number(formData.Odometer),
        nextServiceDue: formData.NextServiceDue,
      };

      await maintenanceLogService.create(maintenanceData);

      navigate("/maintenance");
    } catch (error) {
      console.error(error);
      setError(error.response?.data?.message || error.response?.data || "Failed to add maintenance record.");
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="maintenance-page">
      <div className="page-header">
        <div>
          <h1>Add Maintenance</h1>
          <p>Enter the maintenance and service details below.</p>
        </div>
      </div>

      <form className="maintenance-form" onSubmit={handleSubmit}>
        {error && <div className="error-message">{String(error)}</div>}

        {/* Maintenance Date + Bus */}
        <div className="form-row">
          <div className="form-group">
            <label>Maintenance Date</label>
            <input type="date" name="MaintenanceDate" value={formData.MaintenanceDate} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label>Bus Registration No</label>
            <input
              type="text"
              name="busRegistrationNo"
              value={formData.busRegistrationNo}
              onChange={handleChange}
              placeholder="ND-4521"
              maxLength={20}
              required
            />
          </div>
        </div>

        {/* Maintenance Type + Cost */}
        <div className="form-row">
          <div className="form-group">
            <label>Maintenance Type</label>
            <select name="MType" value={formData.MType} onChange={handleChange} required>
              <option value="">Select maintenance type</option>

              {maintenanceTypes.map((type) => (
                <option key={type} value={type}>
                  {type}
                </option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label>Maintenance Cost (Rs.)</label>
            <input type="number" name="Cost" value={formData.Cost} onChange={handleChange} min="0.01" step="0.01" placeholder="18500.00" required />
          </div>
        </div>

        {/* Odometer + Next Service */}
        <div className="form-row">
          <div className="form-group">
            <label>Odometer Reading (km)</label>
            <input type="number" name="Odometer" value={formData.Odometer} onChange={handleChange} min="0" step="0.01" placeholder="12720" required />
          </div>

          <div className="form-group">
            <label>Next Service Due</label>
            <input type="date" name="NextServiceDue" value={formData.NextServiceDue} onChange={handleChange} required />
          </div>
        </div>

        {/* Description */}
        <div className="form-group">
          <label>Description</label>
          <textarea
            name="Description"
            value={formData.Description}
            onChange={handleChange}
            maxLength={255}
            rows="4"
            placeholder="Engine oil and filter replacement"
            required
          />
        </div>

        {/* Buttons */}
        <div className="form-actions">
          <Button type="button" onClick={() => navigate("/maintenance")} disabled={saving}>
            Cancel
          </Button>

          <Button type="submit" disabled={saving}>
            {saving ? "Saving..." : "Create Maintenance Record"}
          </Button>
        </div>
      </form>
    </div>
  );
}

export default AddMaintenance;
