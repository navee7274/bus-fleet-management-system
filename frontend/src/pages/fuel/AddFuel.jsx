import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Button from "../../components/Button";
import fuelService from "../../services/fuelService";
import "../../styles/Fuel.css";

function AddFuel() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    fDate: "",
    busRegistrationNo: "",
    fPrice: "",
    fLitersfilled: "",
    fCost: "",
  });

  const [error, setError] = useState("");
  const [saving, setSaving] = useState(false);

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((current) => {
      const updated = {
        ...current,
        [name]: value,
      };

      const price = Number(updated.fPrice);
      const cost = Number(updated.fCost);

      if (name === "fPrice" || name === "fCost") {
        updated.fLitersfilled = updated.fPrice !== "" && updated.fCost !== "" && price > 0 ? (cost / price).toFixed(2) : "";
      }

      return updated;
    });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      setSaving(true);
      setError("");

      const payload = {
        fDate: formData.fDate,
        bus: {
          busRegistrationNo: formData.busRegistrationNo.trim(),
        },
        fPrice: Number(formData.fPrice),
        fLitersfilled: Number(formData.fLitersfilled),
        fCost: Number(formData.fCost),
      };

      await fuelService.create(payload);

      navigate("/fuel");
    } catch (error) {
      console.error(error);
      setError(error.response?.data?.message || "Failed to add fuel record.");
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="fuel-page">
      <div className="page-header">
        <div>
          <h1>Add Fuel Record</h1>
          <p>Record fuel purchases and expenses.</p>
        </div>
      </div>

      <form className="fuel-form" onSubmit={handleSubmit}>
        {error && <div className="error-message">{error}</div>}

        {/* Date + Bus */}
        <div className="form-row">
          <div className="form-group">
            <label>Fuel Date</label>
            <input type="date" name="fDate" value={formData.fDate} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label>Bus Registration No</label>
            <input
              type="text"
              name="busRegistrationNo"
              value={formData.busRegistrationNo}
              onChange={handleChange}
              placeholder="CC-3322"
              maxLength={20}
              required
            />
          </div>
        </div>

        {/* Price + Liters */}
        {/* Price + Liters */}
        <div className="form-row">
          <div className="form-group">
            <label>Price per Liter (Rs.)</label>
            <input type="number" name="fPrice" value={formData.fPrice} onChange={handleChange} min="0.01" step="0.01" placeholder="370.00" required />
          </div>

          <div className="form-group">
            <label>Liters Filled (L)</label>
            <input type="number" name="fLitersfilled" value={formData.fLitersfilled} readOnly />
          </div>
        </div>

        {/* Total Cost */}
        <div className="form-group">
          <label>Total Cost (Rs.)</label>
          <input type="number" name="fCost" value={formData.fCost} onChange={handleChange} min="0.01" step="0.01" placeholder="29600.00" required />
        </div>

        {/* Buttons */}
        <div className="form-actions">
          <Button type="button" onClick={() => navigate("/fuel")}>
            Cancel
          </Button>

          <Button type="submit" disabled={saving}>
            {saving ? "Saving..." : "Create Fuel Record"}
          </Button>
        </div>
      </form>
    </div>
  );
}

export default AddFuel;
