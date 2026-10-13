import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import maintenanceLogService from "../../services/maintenanceLogService";
import Loading from "../../components/Loading";
import Button from "../../components/Button";
import Modal from "../../components/Modal";
import "../../styles/Maintenance.css";

function MaintenanceList() {
  const navigate = useNavigate();

  const [records, setRecords] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [selectedRecord, setSelectedRecord] = useState(null);
  const [deleting, setDeleting] = useState(false);

  useEffect(() => {
    loadRecords();
  }, []);

  const loadRecords = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await maintenanceLogService.getAll();
      setRecords(Array.isArray(data) ? data : []);
    } catch (error) {
      console.error("Failed to load maintenance records:", error);
      setError("Failed to load maintenance records. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  const confirmDelete = (record) => {
    setSelectedRecord(record);
    setShowDeleteModal(true);
  };

  const handleDelete = async () => {
    if (!selectedRecord || deleting) return;

    try {
      setDeleting(true);
      setError("");

      await maintenanceLogService.delete(selectedRecord.MaintenanceID);

      setRecords((currentRecords) => currentRecords.filter((record) => record.MaintenanceID !== selectedRecord.MaintenanceID));

      setShowDeleteModal(false);
      setSelectedRecord(null);
    } catch (error) {
      console.error("Failed to delete maintenance record:", error);
      setError("Failed to delete maintenance record. Please try again.");
    } finally {
      setDeleting(false);
    }
  };

  const formatCurrency = (value) => {
    const amount = value?.parsedValue ?? value;

    if (amount == null || amount === "") return "-";

    return `Rs. ${Number(amount).toLocaleString("en-LK", {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2,
    })}`;
  };

  const formatOdometer = (value) => {
    const distance = value?.parsedValue ?? value;

    if (distance == null || distance === "") return "-";

    return `${Number(distance).toLocaleString("en-LK")} km`;
  };

  if (loading) {
    return <Loading message="Loading maintenance records..." />;
  }

  return (
    <div className="maintenance-page">
      <div className="page-header">
        <div>
          <h1>Maintenance Management</h1>
          <p>Manage bus maintenance and service records.</p>
        </div>

        <Button onClick={() => navigate("/maintenance/add")}>+ Add Maintenance</Button>
      </div>

      {error && (
        <div className="error-message">
          {error}
          <button type="button" onClick={loadRecords} style={{ marginLeft: "12px" }}>
            Retry
          </button>
        </div>
      )}

      <div className="maintenance-table-container">
        {records.length === 0 ? (
          <div className="empty-state">
            <h3>No maintenance records found</h3>
            <p>Add your first maintenance record to get started.</p>
          </div>
        ) : (
          <table className="maintenance-table">
            <thead>
              <tr>
                <th>Maintenance ID</th>
                <th>Date</th>
                <th>Bus</th>
                <th>Type</th>
                <th>Description</th>
                <th>Odometer</th>
                <th>Cost</th>
                <th>Next Service Due</th>
                <th>Actions</th>
              </tr>
            </thead>

            <tbody>
              {records.map((record) => (
                <tr key={record.MaintenanceID}>
                  <td>{String(record.MaintenanceID).padStart(8, "0")}</td>

                  <td>{record.MaintenanceDate || "-"}</td>

                  <td>{record.bus?.busRegistrationNo || "-"}</td>

                  <td>{record.MType || "-"}</td>

                  <td>{record.Description || "-"}</td>

                  <td>{formatOdometer(record.Odometer)}</td>

                  <td>{formatCurrency(record.Cost)}</td>

                  <td>{record.NextServiceDue || "-"}</td>

                  <td>
                    <div className="action-buttons">
                      <button type="button" className="action-view" onClick={() => navigate(`/maintenance/${record.MaintenanceID}`)}>
                        View
                      </button>

                      <button type="button" className="action-edit" onClick={() => navigate(`/maintenance/${record.MaintenanceID}/edit`)}>
                        Edit
                      </button>

                      <button type="button" className="action-delete" onClick={() => confirmDelete(record)}>
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

      <Modal
        isOpen={showDeleteModal}
        onClose={() => {
          if (!deleting) {
            setShowDeleteModal(false);
            setSelectedRecord(null);
          }
        }}
        title="Delete Maintenance Record"
      >
        <p>
          Are you sure you want to delete maintenance record <strong>#{selectedRecord?.MaintenanceID}</strong>?
        </p>

        <p>This action cannot be undone.</p>

        <div className="modal-actions">
          <Button
            className="btn-secondary"
            onClick={() => {
              setShowDeleteModal(false);
              setSelectedRecord(null);
            }}
            disabled={deleting}
          >
            Cancel
          </Button>

          <Button className="btn-danger" onClick={handleDelete} disabled={deleting}>
            {deleting ? "Deleting..." : "Delete"}
          </Button>
        </div>
      </Modal>
    </div>
  );
}

export default MaintenanceList;
