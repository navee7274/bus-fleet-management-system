import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import journeyService from "../../services/journeyService";
import Loading from "../../components/Loading";
import Button from "../../components/Button";
import Modal from "../../components/Modal";
import "../../styles/Journey.css";

function JourneyList() {
  const navigate = useNavigate();

  const [journeys, setJourneys] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [selectedJourney, setSelectedJourney] = useState(null);

  useEffect(() => {
    loadJourneys();
  }, []);

  const loadJourneys = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await journeyService.getAllJourneys();
      setJourneys(data);
    } catch (error) {
      console.error(error);
      setError("Failed to load journeys.");
    } finally {
      setLoading(false);
    }
  };

  const confirmDelete = (journey) => {
    setSelectedJourney(journey);
    setShowDeleteModal(true);
  };

  const handleDelete = async () => {
    if (!selectedJourney) return;

    try {
      await journeyService.deleteJourney(selectedJourney.journeyID);

      setJourneys((currentJourneys) => currentJourneys.filter((journey) => journey.journeyID !== selectedJourney.journeyID));

      setShowDeleteModal(false);
      setSelectedJourney(null);
    } catch (error) {
      console.error(error);
      setError("Failed to delete journey.");
    }
  };

  if (loading) {
    return <Loading message="Loading journeys..." />;
  }

  return (
    <div className="journey-page">
      {/* Header */}
      <div className="page-header">
        <div>
          <h1>Journey Management</h1>
          <p>Manage your bus journeys.</p>
        </div>

        <Button onClick={() => navigate("/journeys/add")}>+ Add Journey</Button>
      </div>

      {/* Error */}
      {error && <div className="error-message">{error}</div>}

      {/* Table */}
      <div className="journey-table-container">
        {journeys.length === 0 ? (
          <div className="empty-state">
            <h3>No journeys found</h3>
            <p>Add your first journey.</p>
          </div>
        ) : (
          <table className="journey-table">
            <thead>
              <tr>
                <th>Journey ID</th>
                <th>Date</th>
                <th>Bus</th>
                <th>Driver</th>
                <th>Purpose</th>
                <th>Destination</th>
                <th>KM Travelled</th>
                <th>Income</th>
                <th>Actions</th>
              </tr>
            </thead>

            <tbody>
              {journeys.map((journey) => (
                <tr key={journey.journeyID}>
                  <td>
                    <strong>{journey.journeyID}</strong>
                  </td>

                  <td>{journey.journeyDate}</td>

                  <td>{journey.bus?.busRegistrationNo || "-"}</td>

                  <td>{journey.driver?.DriverID || "-"}</td>

                  <td>{journey.purpose || "-"}</td>

                  <td>{journey.clientDestination}</td>

                  <td>{Number(journey.kmTraveled).toLocaleString()} km</td>

                  <td>Rs. {Number(journey.incomeAmount).toLocaleString()}</td>

                  <td>
                    <div className="action-buttons">
                      <button className="action-view" onClick={() => navigate(`/journeys/${journey.journeyID}`)}>
                        View
                      </button>

                      <button className="action-edit" onClick={() => navigate(`/journeys/${journey.journeyID}/edit`)}>
                        Edit
                      </button>

                      <button className="action-delete" onClick={() => confirmDelete(journey)}>
                        Delete
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>

      {/* Delete Modal */}
      <Modal isOpen={showDeleteModal} onClose={() => setShowDeleteModal(false)} title="Delete Journey">
        <p>
          Are you sure you want to delete journey <strong>#{selectedJourney?.journeyID}</strong>?
        </p>

        <div className="modal-actions">
          <Button className="btn-secondary" onClick={() => setShowDeleteModal(false)}>
            Cancel
          </Button>

          <Button className="btn-danger" onClick={handleDelete}>
            Delete
          </Button>
        </div>
      </Modal>
    </div>
  );
}

export default JourneyList;
