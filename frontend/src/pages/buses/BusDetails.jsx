import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import busService from "../../services/busService";
import Loading from "../../components/Loading";
import Button from "../../components/Button";
import "../../styles/Bus.css";

function BusDetails() {
  const { registrationNo } = useParams();
  const navigate = useNavigate();

  const [bus, setBus] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    loadBus();
  }, [registrationNo]);

  const loadBus = async () => {
    try {
      setLoading(true);

      const data = await busService.getBusByRegistrationNo(registrationNo);

      setBus(data);
    } catch (error) {
      console.error(error);
      setError("Failed to load bus details.");
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <Loading message="Loading bus..." />;
  }

  if (error) {
    return (
      <div className="bus-page">
        {" "}
        <div className="error-message">{error} </div>{" "}
      </div>
    );
  }

  if (!bus) {
    return (
      <div className="bus-page">
        {" "}
        <div className="empty-state">Bus not found. </div>{" "}
      </div>
    );
  }

  return (
    <div className="bus-page">
      <div className="page-header">
        <div>
          <h1>{bus.busRegistrationNo}</h1>
          <p>Bus details</p>
        </div>

        <Button onClick={() => navigate(`/buses/${encodeURIComponent(bus.busRegistrationNo)}/edit`)}>Edit Bus</Button>
      </div>

      <div className="bus-details-card">
        <div className="detail-item">
          <span>Registration Number</span>
          <strong>{bus.busRegistrationNo}</strong>
        </div>

        <div className="detail-item">
          <span>Purchase Date</span>
          <strong>{bus.purchaseDate}</strong>
        </div>

        <div className="detail-item">
          <span>Purchase Price</span>
          <strong>Rs. {Number(bus.purchasePrice).toLocaleString()}</strong>
        </div>

        <div className="detail-item">
          <span>Capacity</span>
          <strong>{bus.capacity} passengers</strong>
        </div>

        <div className="detail-item">
          <span>Status</span>
          <strong className={bus.active ? "text-success" : "text-danger"}>{bus.active ? "Active" : "Inactive"}</strong>
        </div>

        <div className="detail-item detail-notes">
          <span>Notes</span>
          <p>{bus.notes}</p>
        </div>
      </div>
    </div>
  );
}

export default BusDetails;
