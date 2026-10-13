import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import maintenanceLogService from "../../services/maintenanceLogService";
import Loading from "../../components/Loading";
import Button from "../../components/Button";
import "../../styles/Maintenance.css";

function MaintenanceDetails() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [maintenance, setMaintenance] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadMaintenance = async () => {
      try {
        setLoading(true);
        setError("");

        const data = await maintenanceLogService.getById(id);
        setMaintenance(data);
      } catch (err) {
        console.error(err);
        setError("Failed to load maintenance record.");
      } finally {
        setLoading(false);
      }
    };

    loadMaintenance();
  }, [id]);

  const formatCurrency = (value) => {
    const amount = value?.parsedValue ?? value;

    if (amount == null || amount === "") return "-";

    return Number(amount).toLocaleString("en-LK", {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2,
    });
  };

  const formatNumber = (value) => {
    const number = value?.parsedValue ?? value;

    if (number == null || number === "") return "-";

    return Number(number).toLocaleString("en-LK");
  };

  if (loading) {
    return <Loading />;
  }

  if (error) {
    return (
      <div className="maintenance-details-page">
        <div className="error-message">{error}</div>

        <Button onClick={() => navigate("/maintenance")}>Back to Maintenance</Button>
      </div>
    );
  }

  if (!maintenance) {
    return (
      <div className="maintenance-details-page">
        <div className="empty-state">
          <h3>Maintenance record not found</h3>

          <Button onClick={() => navigate("/maintenance")}>Back to Maintenance</Button>
        </div>
      </div>
    );
  }

  return (
    <div className="maintenance-page">
      {/* Header */}
      <div className="page-header">
        <div>
          <h1>Maintenance #{maintenance.MaintenanceID}</h1>
          <p>View maintenance and service information</p>
        </div>

        <Button onClick={() => navigate(`/maintenance/${maintenance.MaintenanceID}/edit`)}>Edit</Button>
      </div>

      {/* Maintenance Information */}
      <div className="maintenance-details-card">
        <div className="detail-item">
          <span>Maintenance ID</span>
          <strong>{maintenance.MaintenanceID}</strong>
        </div>

        <div className="detail-item">
          <span>Maintenance Date</span>
          <strong>{maintenance.MaintenanceDate || "-"}</strong>
        </div>

        <div className="detail-item">
          <span>Bus Registration No</span>
          <strong>{maintenance.bus?.busRegistrationNo || "-"}</strong>
        </div>

        <div className="detail-item">
          <span>Maintenance Type</span>
          <strong>{maintenance.MType || "-"}</strong>
        </div>

        <div className="detail-item">
          <span>Odometer Reading</span>
          <strong>{formatNumber(maintenance.Odometer)}</strong>
          <small>km</small>
        </div>

        <div className="detail-item">
          <span>Maintenance Cost</span>
          <strong>Rs. {formatCurrency(maintenance.Cost)}</strong>
        </div>

        <div className="detail-item">
          <span>Next Service Due</span>
          <strong>{maintenance.NextServiceDue || "-"}</strong>
        </div>

        <div className="detail-item detail-full">
          <span>Description</span>
          <strong>{maintenance.Description || "-"}</strong>
        </div>
      </div>

      <div style={{ marginTop: "20px" }}>
        <Button onClick={() => navigate("/maintenance")} className="btn-secondary">
          Back to Maintenance
        </Button>
      </div>
    </div>
  );
}

export default MaintenanceDetails;
