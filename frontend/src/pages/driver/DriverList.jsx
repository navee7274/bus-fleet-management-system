import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import driverService from "../../services/driverService";
import Loading from "../../components/Loading";
import Button from "../../components/Button";
import "../../styles/Driver.css";

function DriverList() {
  const navigate = useNavigate();

  const [drivers, setDrivers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    loadDrivers();
  }, []);

  const loadDrivers = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await driverService.getAllDrivers();
      setDrivers(data);
    } catch (error) {
      console.error(error);
      setError("Failed to load drivers.");
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <Loading message="Loading drivers..." />;
  }

  return (
    <div className="driver-page">
      {/* Header */}
      <div className="page-header">
        <div>
          <h1>Driver Management</h1>
          <p>Manage your drivers</p>
        </div>
        <Button onClick={() => navigate("/drivers/add")}>+ Add Driver</Button>
      </div>

      {/* Error */}
      {error && <div className="error-message">{error}</div>}

      <div className="driver-table-container">
        {drivers.length === 0 ? (
          <div className="empty-state">
            <h3>No drivers found</h3>
            <p>Add your first driver.</p>
          </div>
        ) : (
          <table className="bus-table">
            <thead>
              <tr>
                <th>Driver ID</th>
                <th>Name</th>
                <th>NIC</th>
                <th>Contact No.</th>
                <th>Base Salary</th>
                <th>Per Trip Allowence</th>
                <th>Active</th>
                <th>Actions</th>
              </tr>
            </thead>

            <tbody>
              {drivers.map((driver) => (
                <tr key={driver.DriverID}>
                  <td>
                    <strong>{driver.DriverID}</strong>
                  </td>
                  <td>{driver.Name}</td>
                  <td>{driver.NIC}</td>
                  <td>{driver.ContactNo}</td>
                  <td>{driver.BaseMonthlySalary}</td>
                  <td>{driver.PerTripAllowence}</td>

                  <td>
                    <span className={`status ${driver.DActive ? "status-active" : "status-inactive"}`}>{driver.DActive ? "Active" : "Inactive"}</span>
                  </td>

                  <td>
                    <div className="action-buttons">
                      <button className="action-view" onClick={() => navigate(`/drivers/${encodeURIComponent(driver.DriverID)}`)}>
                        View
                      </button>

                      <button className="action-edit" onClick={() => navigate(`/drivers/${encodeURIComponent(driver.DriverID)}/edit`)}>
                        Edit
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}

export default DriverList;
