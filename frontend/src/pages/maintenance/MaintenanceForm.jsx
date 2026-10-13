import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import maintenanceLogService from "../../services/maintenanceLogService";
import Button from "../../components/Button";
import Loading from "../../components/Loading";
import "../../styles/Maintenance.css";

function MaintenanceForm() {
  const navigate = useNavigate();
  const { id } = useParams();

  const isEdit = Boolean(id);

  const [formData, setFormData] = useState({
    maintenanceDate: "",
    busRegistrationNo: "",
    MType: "",
    description: "",
    cost: "",
    odometer: "",
    nextServiceDue: "",
  });

  const [loading, setLoading] = useState(false);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  const maintenanceTypes = ["Service", "Repair"];

  /*
   * Load maintenance record when editing
   */
  useEffect(() => {
    if (!isEdit) return;

    const loadMaintenance = async () => {
      try {
        setLoading(true);
        setError("");

        const data = await maintenanceLogService.getById(id);

        setFormData({
          maintenanceDate: data.MaintenanceDate || data.maintenanceDate || "",
          busRegistrationNo: data.bus?.busRegistrationNo || "",
          MType: data.MType || data.mtype || "",
          description: data.Description || data.description || "",
          cost: data.Cost?.parsedValue ?? data.cost?.parsedValue ?? data.Cost ?? data.cost ?? "",
          odometer: data.Odometer?.parsedValue ?? data.odometer?.parsedValue ?? data.Odometer ?? data.odometer ?? "",
          nextServiceDue: data.NextServiceDue || data.nextServiceDue || "",
        });
      } catch (err) {
        console.error(err);
        setError("Failed to load maintenance record.");
      } finally {
        setLoading(false);
      }
    };

    loadMaintenance();
  }, [id, isEdit]);

  /*
   * Handle input changes
   */
  const handleChange = (e) => {
    const { name, value } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  /*
   * Submit form
   */
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (!formData.maintenanceDate) {
      setError("Please select the maintenance date.");
      return;
    }

    if (!formData.busRegistrationNo.trim()) {
      setError("Please enter the bus registration number.");
      return;
    }

    if (!formData.MType) {
      setError("Please select a maintenance type.");
      return;
    }

    if (!formData.description.trim()) {
      setError("Please enter a description.");
      return;
    }

    if (formData.cost === "" || Number(formData.cost) <= 0) {
      setError("Maintenance cost must be greater than zero.");
      return;
    }

    if (formData.odometer === "" || Number(formData.odometer) < 0) {
      setError("Odometer reading cannot be negative.");
      return;
    }

    if (!formData.nextServiceDue) {
      setError("Please select the next service due date.");
      return;
    }

    try {
      setSaving(true);

      /*
       * Request body matching MaintenanceLogRequest DTO
       */
      const maintenanceData = {
        maintenanceDate: formData.maintenanceDate,
        busRegistrationNo: formData.busRegistrationNo.trim(),
        MType: formData.MType,
        description: formData.description.trim(),
        cost: Number(formData.cost),
        odometer: Number(formData.odometer),
        nextServiceDue: formData.nextServiceDue,
      };

      if (isEdit) {
        await maintenanceLogService.update(id, maintenanceData);
      } else {
        await maintenanceLogService.create(maintenanceData);
      }

      navigate("/maintenance");
    } catch (err) {
      console.error(err);

      setError(
        err.response?.data?.message ||
          (typeof err.response?.data === "string" ? err.response.data : "") ||
          `Failed to ${isEdit ? "update" : "add"} maintenance record.`,
      );
    } finally {
      setSaving(false);
    }
  };

  if (loading) {
    return <Loading />;
  }

  return (
    <div className="maintenance-page">
      <div className="page-header">
        <div>
          <h1>{isEdit ? "Edit Maintenance" : "Add Maintenance"}</h1>
          <p>{isEdit ? "Update maintenance and service information." : "Enter the maintenance and service details below."}</p>
        </div>
      </div>

      <form className="maintenance-form" onSubmit={handleSubmit}>
        {error && <div className="error-message">{error}</div>}

        {/* Maintenance Date + Bus */}
        <div className="form-row">
          <div className="form-group">
            <label>Maintenance Date</label>

            <input type="date" name="maintenanceDate" value={formData.maintenanceDate} onChange={handleChange} required />
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

            <input type="number" name="cost" value={formData.cost} onChange={handleChange} min="0.01" step="0.01" placeholder="18500.00" required />
          </div>
        </div>

        {/* Odometer + Next Service Due */}
        <div className="form-row">
          <div className="form-group">
            <label>Odometer Reading (km)</label>

            <input type="number" name="odometer" value={formData.odometer} onChange={handleChange} min="0" step="0.01" placeholder="12720" required />
          </div>

          <div className="form-group">
            <label>Next Service Due</label>

            <input type="date" name="nextServiceDue" value={formData.nextServiceDue} onChange={handleChange} required />
          </div>
        </div>

        {/* Description */}
        <div className="form-group">
          <label>Description</label>

          <textarea
            name="description"
            value={formData.description}
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
            {saving ? "Saving..." : isEdit ? "Update Maintenance" : "Create Maintenance Record"}
          </Button>
        </div>
      </form>
    </div>
  );
}

export default MaintenanceForm;
