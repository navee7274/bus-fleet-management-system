import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import journeyService from "../../services/journeyService";
import Loading from "../../components/Loading";
import Button from "../../components/Button";
import "../../styles/Journey.css";

function JourneyDetails() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [journey, setJourney] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadJourney = async () => {
      try {
        setLoading(true);

        const data = await journeyService.getById(id);
        setJourney(data);
      } catch (err) {
        console.error(err);
        setError("Failed to load journey.");
      } finally {
        setLoading(false);
      }
    };

    loadJourney();
  }, [id]);

  if (loading) {
    return <Loading />;
  }

  if (error) {
    return (
      <div className="journey-details-page">
        <div className="error-message">{error}</div>

        <Button onClick={() => navigate("/journeys")}>Back to Journeys</Button>
      </div>
    );
  }

  if (!journey) {
    return (
      <div className="journey-details-page">
        <div className="empty-state">
          <h3>Journey not found</h3>

          <Button onClick={() => navigate("/journeys")}>Back to Journeys</Button>
        </div>
      </div>
    );
  }

  return (
    <div className="journey-page">
      {/* Header */}
      <div className="page-header">
        <div>
          <h1>Journey #{journey.journeyID}</h1>
          <p>View journey information</p>
        </div>

        <Button onClick={() => navigate(`/journeys/${journey.journeyID}/edit`)}>Edit</Button>
      </div>

      {/* Journey Information */}
      <div className="journey-details-card">
        <div className="detail-item">
          <span>Journey ID</span>
          <strong>{journey.journeyID}</strong>
        </div>

        <div className="detail-item">
          <span>Journey Date</span>
          <strong>{journey.journeyDate}</strong>
        </div>

        <div className="detail-item">
          <span>Bus Registration No</span>
          <strong>{journey.bus?.busRegistrationNo || "-"}</strong>
        </div>

        <div className="detail-item">
          <span>Driver ID</span>
          <strong>{journey.driver?.DriverID || "-"}</strong>
        </div>

        <div className="detail-item">
          <span>Driver Contact</span>
          <strong>{journey.driver?.ContactNo || "-"}</strong>
        </div>

        <div className="detail-item">
          <span>Purpose</span>
          <strong>{journey.purpose || "-"}</strong>
        </div>

        <div className="detail-item">
          <span>Client Destination</span>
          <strong>{journey.clientDestination}</strong>
        </div>

        <div className="detail-item">
          <span>Start Odometer</span>
          <strong>{journey.startOdometer}</strong>
          <small>km</small>
        </div>

        <div className="detail-item">
          <span>End Odometer</span>
          <strong>{journey.endOdometer}</strong>
          <small>km</small>
        </div>

        <div className="detail-item">
          <span>KM Travelled</span>
          <strong>{journey.kmTraveled}</strong>
          <small>km</small>
        </div>

        <div className="detail-item">
          <span>Income Amount</span>
          <strong>Rs. {journey.incomeAmount}</strong>
        </div>

        <div className="detail-item detail-full">
          <span>Notes</span>
          <strong>{journey.notes || "-"}</strong>
        </div>
      </div>
    </div>
  );
}

export default JourneyDetails;
