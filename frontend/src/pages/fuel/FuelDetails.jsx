import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import fuelService from "../../services/fuelService";
import Loading from "../../components/Loading";
import Button from "../../components/Button";
import "../../styles/Fuel.css";

function FuelDetails() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [record, setRecord] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadRecord = async () => {
      try {
        setLoading(true);
        setError("");

        const data = await fuelService.getById(id);
        setRecord(data);
      } catch (err) {
        console.error(err);
        setError("Failed to load fuel record.");
      } finally {
        setLoading(false);
      }
    };

    loadRecord();
  }, [id]);

  if (loading) {
    return <Loading message="Loading fuel record..." />;
  }

  if (error) {
    return (
      <div className="fuel-details-page">
        <div className="error-message">{error}</div>

        <Button onClick={() => navigate("/fuel")}>Back to Fuel Records</Button>
      </div>
    );
  }

  if (!record) {
    return (
      <div className="fuel-details-page">
        <div className="empty-state">
          <h3>Fuel record not found</h3>

          <Button onClick={() => navigate("/fuel")}>Back to Fuel Records</Button>
        </div>
      </div>
    );
  }

  const price = Number(record.fPrice?.parsedValue ?? record.fPrice ?? 0);
  const liters = Number(record.fLitersfilled?.parsedValue ?? record.fLitersfilled ?? 0);
  const cost = Number(record.fCost?.parsedValue ?? record.fCost ?? 0);

  const formatCurrency = (value) =>
    `Rs. ${value.toLocaleString("en-LK", {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2,
    })}`;

  return (
    <div className="fuel-page">
      {/* Header */}
      <div className="page-header">
        <div>
          <h1>Fuel Record #{record.FuelLogID}</h1>
          <p>View fuel purchase information.</p>
        </div>

        <Button onClick={() => navigate(`/fuel/${record.FuelLogID}/edit`)}>Edit</Button>
      </div>

      {/* Fuel Information */}
      <div className="fuel-details-card">
        <div className="detail-item">
          <span>Fuel Log ID</span>
          <strong>{record.FuelLogID}</strong>
        </div>

        <div className="detail-item">
          <span>Fuel Date</span>
          <strong>{record.fDate || "-"}</strong>
        </div>

        <div className="detail-item">
          <span>Bus Registration No</span>
          <strong>{record.bus?.busRegistrationNo || "-"}</strong>
        </div>

        <div className="detail-item">
          <span>Price per Liter</span>
          <strong>{formatCurrency(price)}</strong>
        </div>

        <div className="detail-item">
          <span>Liters Filled</span>
          <strong>{liters.toLocaleString("en-LK")} L</strong>
        </div>

        <div className="detail-item">
          <span>Total Fuel Cost</span>
          <strong>{formatCurrency(cost)}</strong>
        </div>

        <div className="detail-item">
          <span>Bus Registration No</span>
          <strong>{record.bus?.busRegistrationNo || "-"}</strong>
        </div>
      </div>

      <div className="form-actions">
        <Button onClick={() => navigate("/fuel")}>Back</Button>
      </div>
    </div>
  );
}

export default FuelDetails;
