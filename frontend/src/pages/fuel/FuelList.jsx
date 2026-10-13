import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import fuelService from "../../services/fuelService";
import Loading from "../../components/Loading";
import Button from "../../components/Button";
import Modal from "../../components/Modal";
import "../../styles/Fuel.css";

function FuelList() {
  const navigate = useNavigate();

  const [records, setRecords] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [selectedRecord, setSelectedRecord] = useState(null);

  useEffect(() => {
    loadRecords();
  }, []);

  const loadRecords = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await fuelService.getAll();
      setRecords(data);
    } catch (error) {
      console.error(error);
      setError("Failed to load fuel records.");
    } finally {
      setLoading(false);
    }
  };

  const confirmDelete = (record) => {
    setSelectedRecord(record);
    setShowDeleteModal(true);
  };

  const handleDelete = async () => {
    if (!selectedRecord) return;

    try {
      await fuelService.delete(selectedRecord.FuelLogID);

      setRecords((currentRecords) => currentRecords.filter((record) => record.FuelLogID !== selectedRecord.FuelLogID));

      setShowDeleteModal(false);
      setSelectedRecord(null);
    } catch (error) {
      console.error(error);
      setError("Failed to delete fuel record.");
    }
  };

  if (loading) {
    return <Loading message="Loading fuel records..." />;
  }

  return (
    <div className="fuel-page">
      {/* Header */}
      <div className="page-header">
        <div>
          <h1>Fuel Management</h1>
          <p>Track fuel purchases and expenses.</p>
        </div>

        <Button onClick={() => navigate("/fuel/add")}>+ Add Fuel Record</Button>
      </div>

      {/* Error */}
      {error && <div className="error-message">{error}</div>}

      {/* Table */}
      <div className="fuel-table-container">
        {records.length === 0 ? (
          <div className="empty-state">
            <h3>No fuel records found</h3>
            <p>Add your first fuel record.</p>
          </div>
        ) : (
          <table className="fuel-table">
            <thead>
              <tr>
                <th>Fuel Log ID</th>
                <th>Date</th>
                <th>Bus</th>
                <th>Price per Liter</th>
                <th>Liters Filled</th>
                <th>Total Cost</th>
                <th>Actions</th>
              </tr>
            </thead>

            <tbody>
              {records.map((record) => (
                <tr key={record.FuelLogID}>
                  <td>
                    <strong>{record.FuelLogID.toString().padStart(8, "0")}</strong>
                  </td>

                  <td>{record.fDate || "-"}</td>

                  <td>{record.bus?.busRegistrationNo || "-"}</td>

                  <td>Rs. {Number(record.fPrice?.parsedValue ?? record.fPrice ?? 0).toLocaleString("en-LK")}</td>

                  <td>{Number(record.fLitersfilled?.parsedValue ?? record.fLitersfilled ?? 0).toLocaleString("en-LK")} L</td>

                  <td>
                    <strong>Rs. {Number(record.fCost?.parsedValue ?? record.fCost ?? 0).toLocaleString("en-LK")}</strong>
                  </td>

                  <td>
                    <div className="action-buttons">
                      <button className="action-view" onClick={() => navigate(`/fuel/${record.FuelLogID}`)}>
                        View
                      </button>

                      <button className="action-edit" onClick={() => navigate(`/fuel/${record.FuelLogID}/edit`)}>
                        Edit
                      </button>

                      <button className="action-delete" onClick={() => confirmDelete(record)}>
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
      <Modal isOpen={showDeleteModal} onClose={() => setShowDeleteModal(false)} title="Delete Fuel Record">
        <p>
          Are you sure you want to delete fuel record <strong>#{selectedRecord?.FuelLogID}</strong>?
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

export default FuelList;
