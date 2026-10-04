import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import busService from "../../services/busService";
import Loading from "../../components/Loading";
import Button from "../../components/Button";
import "../../styles/Bus.css";

function EditBus() {
  const { registrationNo } = useParams();
  const navigate = useNavigate();

  const [formData, setFormData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    loadBus();
  }, [registrationNo]);

  const loadBus = async () => {
    try {
      const data = await busService.getBusByRegistrationNo(registrationNo);

      setFormData({
        busRegistrationNo: data.busRegistrationNo,
        purchaseDate: data.purchaseDate,
        purchasePrice: data.purchasePrice,
        capacity: data.capacity,
        notes: data.notes,
        active: data.active,
      });
    } catch (error) {
      console.error(error);
      setError("Failed to load bus.");
    } finally {
      setLoading(false);
    }
  };

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

      await busService.updateBus(registrationNo, {
        purchaseDate: formData.purchaseDate,
        purchasePrice: Number(formData.purchasePrice),
        capacity: Number(formData.capacity),
        notes: formData.notes,
        active: formData.active,
      });

      navigate(`/buses/${encodeURIComponent(registrationNo)}`);
    } catch (error) {
      console.error(error);

      setError(error.response?.data?.message || "Failed to update bus.");
    } finally {
      setSaving(false);
    }
  };

  if (loading) {
    return <Loading message="Loading bus..." />;
  }

  if (!formData) {
    return (
      <div className="bus-page">
        {" "}
        <div className="error-message">{error || "Bus not found."} </div>{" "}
      </div>
    );
  }

  return (
    <div className="bus-page">
      <div className="page-header">
        <div>
          <h1>Edit Bus</h1>
          <p>Update {formData.busRegistrationNo}</p>
        </div>
      </div>

      <form className="bus-form" onSubmit={handleSubmit}>
        {error && <div className="error-message">{error}</div>}

        <div className="form-group">
          <label>Registration Number</label>

          <input type="text" value={formData.busRegistrationNo} disabled />
        </div>

        <div className="form-row">
          <div className="form-group">
            <label>Purchase Date</label>

            <input type="date" name="purchaseDate" value={formData.purchaseDate} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label>Purchase Price</label>

            <input type="number" name="purchasePrice" value={formData.purchasePrice} onChange={handleChange} min="0" step="0.01" required />
          </div>
        </div>

        <div className="form-group">
          <label>Capacity</label>

          <input type="number" name="capacity" value={formData.capacity} onChange={handleChange} min="1" required />
        </div>

        <div className="form-group">
          <label>Notes</label>

          <textarea name="notes" value={formData.notes} onChange={handleChange} maxLength="225" rows="4" required />
        </div>

        <div className="checkbox-group">
          <input type="checkbox" id="active" name="active" checked={formData.active} onChange={handleChange} />

          <label htmlFor="active">Bus is active</label>
        </div>

        <div className="form-actions">
          <Button type="button" className="btn-secondary" onClick={() => navigate(`/buses/${encodeURIComponent(registrationNo)}`)}>
            Cancel
          </Button>

          <Button type="submit" disabled={saving}>
            {saving ? "Saving..." : "Save Changes"}
          </Button>
        </div>
      </form>
    </div>
  );
}

export default EditBus;
