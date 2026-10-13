import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import fuelService from "../../services/fuelService";
import Button from "../../components/Button";
import Loading from "../../components/Loading";
import "../../styles/Fuel.css";

function FuelForm() {
  const navigate = useNavigate();
  const { id } = useParams();

  const isEdit = Boolean(id);

  const [formData, setFormData] = useState({
    fDate: "",
    busRegistrationNo: "",
    fPrice: "",
    fLitersfilled: "",
    fCost: "",
  });

  const [loading, setLoading] = useState(false);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  // Load fuel record when editing
  useEffect(() => {
    if (!isEdit) return;

    const loadRecord = async () => {
      try {
        setLoading(true);
        setError("");

        const data = await fuelService.getById(id);

        const price = data.fPrice?.parsedValue ?? data.fPrice ?? "";
        const cost = data.fCost?.parsedValue ?? data.fCost ?? "";

        setFormData({
          fDate: data.fDate || "",
          busRegistrationNo: data.bus?.busRegistrationNo || "",
          fPrice: price,
          fCost: cost,
          fLitersfilled: Number(price) > 0 && cost !== "" ? (Number(cost) / Number(price)).toFixed(2) : "",
        });
      } catch (err) {
        console.error(err);
        setError("Failed to load fuel record.");
      } finally {
        setLoading(false);
      }
    };

    loadRecord();
  }, [id, isEdit]);

  // Handle input changes
  const handleChange = (e) => {
    const { name, value } = e.target;

    setFormData((prev) => {
      const updated = {
        ...prev,
        [name]: value,
      };

      // Calculate liters filled = total cost / price per liter
      if (name === "fPrice" || name === "fCost") {
        const price = Number(updated.fPrice);
        const cost = Number(updated.fCost);

        updated.fLitersfilled = updated.fPrice !== "" && updated.fCost !== "" && price > 0 ? (cost / price).toFixed(2) : "";
      }

      return updated;
    });
  };

  // Submit form
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    const price = Number(formData.fPrice);
    const cost = Number(formData.fCost);

    if (!formData.busRegistrationNo.trim()) {
      setError("Please enter a bus registration number.");
      return;
    }

    if (!formData.fDate) {
      setError("Please select a fuel date.");
      return;
    }

    if (!Number.isFinite(price) || price <= 0) {
      setError("Price per liter must be greater than zero.");
      return;
    }

    if (!Number.isFinite(cost) || cost <= 0) {
      setError("Total cost must be greater than zero.");
      return;
    }

    const liters = cost / price;

    if (!Number.isFinite(liters) || liters <= 0) {
      setError("Please check the fuel price and total cost.");
      return;
    }

    try {
      setSaving(true);

      const fuelData = {
        fDate: formData.fDate,
        bus: {
          busRegistrationNo: formData.busRegistrationNo.trim(),
        },
        fPrice: price,
        fCost: cost,
        fLitersfilled: Number(liters.toFixed(2)),
      };

      if (isEdit) {
        await fuelService.update(id, fuelData);
      } else {
        await fuelService.create(fuelData);
      }

      navigate("/fuel");
    } catch (err) {
      console.error(err);
      setError(err.response?.data?.message || `Failed to ${isEdit ? "update" : "create"} fuel record.`);
    } finally {
      setSaving(false);
    }
  };

  if (loading) {
    return <Loading message="Loading fuel record..." />;
  }

  return (
    <div className="fuel-page">
      <div className="page-header">
        <div>
          <h1>{isEdit ? "Edit Fuel Record" : "Add Fuel Record"}</h1>
          <p>{isEdit ? "Update fuel purchase information." : "Enter your fuel purchase details below."}</p>
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
        <div className="form-row">
          <div className="form-group">
            <label>Price per Liter (Rs.)</label>
            <input type="number" name="fPrice" value={formData.fPrice} onChange={handleChange} min="0.01" step="0.01" placeholder="370.00" required />
          </div>

          <div className="form-group">
            <label>Liters Filled (L)</label>
            <input type="number" name="fLitersfilled" value={formData.fLitersfilled} readOnly />
            <small>Calculated from total cost ÷ price per liter.</small>
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
            {saving ? "Saving..." : isEdit ? "Update Fuel Record" : "Create Fuel Record"}
          </Button>
        </div>
      </form>
    </div>
  );
}

export default FuelForm;
