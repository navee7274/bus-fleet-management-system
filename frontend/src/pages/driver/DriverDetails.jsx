import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Loading from "../../components/Loading";
import Button from "../../components/Button";
import "../../styles/Driver.css";
import driverService from "../../services/driverService";

function DriverDetails() {
  const { DriverID } = useParams();
  const navigate = useNavigate();

  const [driver, setDriver] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    loadDriver();
  }, [DriverID]);

  const loadDriver = async () => {
    try {
      setLoading(true);

      const data = await driverService.getById(DriverID);

      setDriver(data);
    } catch (error) {
      console.error(error);
      setError("Failed to load driver details.");
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <Loading message="Loading driver..." />;
  }

  if (error) {
    return (
      <div className="driver-page">
        {" "}
        <div className="error-message">{error} </div>{" "}
      </div>
    );
  }

  if (!driver) {
    return (
      <div className="driver-page">
        {" "}
        <div className="empty-state">Driver not found. </div>{" "}
      </div>
    );
  }

  return (
    <div className="driver-page">
      <div className="page-header">
        <div>
          <h1>{driver.DriverID}</h1>
          <p>Driver details</p>
        </div>

        <Button onClick={() => navigate(`/drivers/${encodeURIComponent(driver.DriverID)}/edit`)}>Edit Driver</Button>
      </div>

      <div className="driver-details-card">
        <div className="detail-item">
          <span>Driver ID</span>
          <strong>{driver.DriverID}</strong>
        </div>

        <div className="detail-item">
          <span>Driver Name</span>
          <strong>{driver.Name}</strong>
        </div>

        <div className="detail-item">
          <span>Driver NIC</span>
          <strong>{driver.NIC}</strong>
        </div>

        <div className="detail-item">
          <span>Contact Number</span>
          <strong>{driver.ContactNo}</strong>
        </div>

        <div className="detail-item">
          <span>Base Monthly Salary</span>
          <strong>{driver.BaseMonthlySalary}</strong>
        </div>

        <div className="detail-item">
          <span>Per Trip Allowence</span>
          <strong>{driver.PerTripAllowence}</strong>
        </div>

        <div className="detail-item">
          <span>Status</span>
          <strong className={driver.DActive ? "text-success" : "text-danger"}>{driver.DActive ? "Active" : "Inactive"}</strong>
        </div>

        <div className="detail-item detail-notes">
          <span>Notes</span>
          <p>{driver.Notes}</p>
        </div>
      </div>
    </div>
  );
}

export default DriverDetails;
