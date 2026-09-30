import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import busService from "../../services/busService";
import Loading from "../../components/Loading";
import Button from "../../components/Button";
import Modal from "../../components/Modal";
import "../../styles/Bus.css";

function BusList() {
  const navigate = useNavigate();

  const [buses, setBuses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [selectedBus, setSelectedBus] = useState(null);

  useEffect(() => {
    loadBuses();
  }, []);

  const loadBuses = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await busService.getAllBuses();
      setBuses(data);
    } catch (error) {
      console.error(error);
      setError("Failed to load buses.");
    } finally {
      setLoading(false);
    }
  };

  const confirmDelete = (bus) => {
    setSelectedBus(bus);
    setShowDeleteModal(true);
  };

  const handleDelete = async () => {
    if (!selectedBus) return;

    try {
      await busService.deactivateBus(selectedBus.busRegistrationNo);

      setBuses((currentBuses) =>
        currentBuses.map((bus) => (bus.busRegistrationNo === selectedBus.busRegistrationNo ? { ...bus, active: false } : bus)),
      );

      setShowDeleteModal(false);
      setSelectedBus(null);
    } catch (error) {
      console.error(error);
      setError("Failed to deactivate bus.");
    }
  };

  if (loading) {
    return <Loading message="Loading buses..." />;
  }

  return (
    <div className="bus-page">
      <div className="page-header">
        <div>
          <h1>Bus Management</h1>
          <p>Manage your fleet of buses.</p>
        </div>

        <Button onClick={() => navigate("/buses/add")}>+ Add Bus</Button>
      </div>

      {error && <div className="error-message">{error}</div>}

      <div className="bus-table-container">
        {buses.length === 0 ? (
          <div className="empty-state">
            <h3>No buses found</h3>
            <p>Add your first bus to the fleet.</p>
          </div>
        ) : (
          <table className="bus-table">
            <thead>
              <tr>
                <th>Registration No.</th>
                <th>Purchase Date</th>
                <th>Purchase Price</th>
                <th>Capacity</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>

            <tbody>
              {buses.map((bus) => (
                <tr key={bus.busRegistrationNo}>
                  <td>
                    <strong>{bus.busRegistrationNo}</strong>
                  </td>

                  <td>{bus.purchaseDate}</td>

                  <td>Rs. {Number(bus.purchasePrice).toLocaleString()}</td>

                  <td>{bus.capacity}</td>

                  <td>
                    <span className={`status ${bus.active ? "status-active" : "status-inactive"}`}>{bus.active ? "Active" : "Inactive"}</span>
                  </td>

                  <td>
                    <div className="action-buttons">
                      <button className="action-view" onClick={() => navigate(`/buses/${encodeURIComponent(bus.busRegistrationNo)}`)}>
                        View
                      </button>

                      <button className="action-edit" onClick={() => navigate(`/buses/${encodeURIComponent(bus.busRegistrationNo)}/edit`)}>
                        Edit
                      </button>

                      <button className="action-delete" onClick={() => confirmDelete(bus)}>
                        Deactivate
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>

      <Modal isOpen={showDeleteModal} onClose={() => setShowDeleteModal(false)} title="Deactivate Bus">
        <p>
          Are you sure you want to deactivate <strong>{selectedBus?.busRegistrationNo}</strong>?
        </p>

        <div className="modal-actions">
          <Button className="btn-secondary" onClick={() => setShowDeleteModal(false)}>
            Cancel
          </Button>

          <Button className="btn-danger" onClick={handleDelete}>
            Deactivate
          </Button>
        </div>
      </Modal>
    </div>
  );
}

export default BusList;
