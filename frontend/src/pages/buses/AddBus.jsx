import { useState } from "react";
import { useNavigate } from "react-router-dom";
import busService from "../../services/busService";
import Button from "../../components/Button";
import "../../styles/Bus.css";

function AddBus() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    busRegistrationNo: "",
    purchaseDate: "",
    purchasePrice: "",
    capacity: "",
    notes: "",
    active: true,
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

      await busService.createBus({
        busRegistrationNo: formData.bRegistrationNo,
        purchaseDate: formData.purchaseDate,
        purchasePrice: Number(formData.purchasePrice),
        capacity: Number(formData.capacity),
        notes: formData.notes,
        active: formData.active,
      });

      navigate("/buses");
    } catch (error) {
      console.error(error);
      setError(error.response?.data?.message || "Failed to create bus.");
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="bus-page">
      <div className="page-header">
        <div>
          <h1>Add Bus</h1>
          <p>Add a new bus to your fleet.</p>
        </div>
      </div>

      <form className="driver-form" onSubmit={handleSubmit}>
        {error && <div className="error-message">{error}</div>}

        <div className="form-row">
          <div className="form-group">
            <label>Registration Number</label>

            <input
              type="text"
              name="bRegistrationNo"
              value={formData.bRegistrationNo}
              onChange={handleChange}
              placeholder="WP CAA-1234"
              maxLength="20"
              required
            />
          </div>

          <div className="form-group">
            <label>Purchase Date</label>

            <input type="date" name="purchaseDate" value={formData.purchaseDate} onChange={handleChange} required />
          </div>
        </div>

        <div className="form-row">
          <div className="form-group">
            <label>Purchase Price</label>

            <input
              type="number"
              name="purchasePrice"
              value={formData.purchasePrice}
              onChange={handleChange}
              min="0"
              step="0.01"
              placeholder="0.00"
              required
            />
          </div>

          <div className="form-group">
            <label>Capacity</label>

            <input
              type="number"
              name="capacity"
              value={formData.capacity}
              onChange={handleChange}
              min="1"
              placeholder="Number of passengers"
              required
            />
          </div>
        </div>

        <div className="form-group">
          <label>Notes</label>

          <textarea
            name="notes"
            value={formData.notes}
            onChange={handleChange}
            maxLength="225"
            rows="4"
            placeholder="Additional information about the bus..."
            required
          />
        </div>

        <div className="checkbox-group">
          <input type="checkbox" id="active" name="active" checked={formData.active} onChange={handleChange} />

          <label htmlFor="active">Bus is active</label>
        </div>

        <div className="form-actions">
          <Button type="button" className="btn-secondary" onClick={() => navigate("/buses")}>
            Cancel
          </Button>

          <Button type="submit" disabled={saving}>
            {saving ? "Saving..." : "Add Bus"}
          </Button>
        </div>
      </form>
    </div>
  );
}

export default AddBus;
