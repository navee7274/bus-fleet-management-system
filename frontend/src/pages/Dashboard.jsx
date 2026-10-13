import { useAuth } from "../context/AuthContext";
import Navbar from "../components/Navbar";
import Sidebar from "../components/Sidebar";
import Button from "../components/Button";
import Loading from "../components/Loading";
import "../styles/Dashboard.css";
import { useEffect, useState } from "react";
import dashboardService from "../services/dashboardService";
import { useNavigate } from "react-router-dom";

function Dashboard() {
  const { user } = useAuth();

  const navigate = useNavigate();

  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadDashboard = async () => {
      try {
        setLoading(true);
        setError("");

        const result = await dashboardService.getSummary();
        console.log("Dashboard API response:", result);

        setData(result);
      } catch (error) {
        console.error("Dashboard loading error:", error);
        setError("Failed to load dashboard data.");
      } finally {
        setLoading(false);
      }
    };

    loadDashboard();
  }, []);

  if (loading) {
    return (
      <div className="dashboard">
        <Sidebar />

        <div className="dashboard-main">
          <Navbar />

          <main className="dashboard-content">
            <Loading message="Loading dashboard..." />
          </main>
        </div>
      </div>
    );
  }

  if (error) {
    return <p>{error}</p>;
  }
  console.log(data);

  return (
    <div className="dashboard">
      <Sidebar />

      <div className="dashboard-main">
        <Navbar />

        <main className="dashboard-content">
          {/* Header */}
          <div className="dashboard-header">
            <div>
              <h1>Dashboard</h1>
              <p>Welcome back, {user?.username || "Admin"} 👋</p>
            </div>

            <div className="dashboard-date">
              {new Date().toLocaleDateString("en-GB", {
                weekday: "long",
                day: "numeric",
                month: "long",
                year: "numeric",
              })}
            </div>
          </div>

          {/* Statistics */}

          <div className="stats-grid">
            <div className="stat-card">
              <div className="stat-icon">🚌</div>
              <div>
                <span>Total Buses</span>
                <h2>{data.totalBuses}</h2>
              </div>
            </div>

            <div className="stat-card">
              <div className="stat-icon">📋</div>
              <div>
                <span>Active Bookings</span>
                <h2>{data.activeBookings}</h2>
              </div>
            </div>

            <div className="stat-card">
              <div className="stat-icon">🛣️</div>
              <div>
                <span>Today's Journeys</span>
                <h2>{data.todaysJourneys}</h2>
              </div>
            </div>

            <div className="stat-card">
              <div className="stat-icon">💰</div>
              <div>
                <span>Monthly Revenue</span>
                <h2>Rs. {Number(data.monthlyRevenue?.parsedValue ?? data.monthlyRevenue ?? 0).toLocaleString("en-LK")}</h2>
              </div>
            </div>
          </div>

          {/* Main Grid */}
          <div className="dashboard-grid">
            {/* Recent Bookings */}
            <section className="dashboard-card bookings-card">
              <div className="card-header">
                <div>
                  <h2>Recent Bookings</h2>
                  <p>Latest customer bookings</p>
                </div>

                <Button className="view-button" onClick={() => navigate("/bookings")}>
                  View All
                </Button>
              </div>

              <div className="table-container">
                <table>
                  <thead>
                    <tr>
                      <th>Booking</th>
                      <th>Customer</th>
                      <th>Route</th>
                      <th>Status</th>
                    </tr>
                  </thead>

                  <tbody>
                    {data.recentBookings.map((booking) => (
                      <tr key={booking.bookingID}>
                        <td>#{booking.bookingID}</td>
                        <td>{booking.customerName}</td>
                        <td>
                          {booking.startLocation} → {booking.destination}
                        </td>
                        <td>
                          <span className={`status status-${booking.status.toLowerCase().replaceAll("_", "-")}`}>
                            {booking.status.replaceAll("_", " ")}
                          </span>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </section>

            {/* Fleet Status */}
            <section className="dashboard-card fleet-card">
              <div className="card-header">
                <div>
                  <h2>Fleet Status</h2>
                  <p>Current bus availability</p>
                </div>
              </div>

              <div className="fleet-status">
                <div className="fleet-item">
                  <div className="fleet-label">
                    <span className="fleet-dot available"></span>
                    Active Buses
                  </div>
                  <strong>{data.activeBuses}</strong>
                </div>

                <div className="fleet-item">
                  <div className="fleet-label">
                    <span className="fleet-dot active"></span>
                    Active Drivers
                  </div>
                  <strong>{data.activeDrivers}</strong>
                </div>

                <div className="fleet-item">
                  <div className="fleet-label">
                    <span className="fleet-dot maintenance"></span>
                    Inactive Buses
                  </div>
                  <strong>{data.totalBuses - data.activeBuses}</strong>
                </div>
              </div>

              <div className="fleet-total">
                <span>Total Fleet</span>
                <strong>{data.totalBuses} Buses</strong>
              </div>
            </section>
          </div>

          {/* Bottom Grid */}
          <div className="dashboard-grid bottom-grid">
            {/* Today's Journeys */}

            <section className="dashboard-card">
              <div className="card-header">
                <div>
                  <h2>Payment Overview</h2>
                  <p>Current payment status</p>
                </div>
              </div>

              <div className="fleet-status">
                <div className="fleet-item">
                  <div className="fleet-label">
                    <span className="fleet-dot available"></span>
                    Successful
                  </div>
                  <strong>{data.successfulPayments}</strong>
                </div>

                <div className="fleet-item">
                  <div className="fleet-label">
                    <span className="fleet-dot active"></span>
                    Pending
                  </div>
                  <strong>{data.pendingPayments}</strong>
                </div>

                <div className="fleet-item">
                  <div className="fleet-label">
                    <span className="fleet-dot maintenance"></span>
                    Failed
                  </div>
                  <strong>{data.failedPayments}</strong>
                </div>
              </div>

              <div className="fleet-total">
                <span>Total Payments</span>
                <strong>{data.totalPayments}</strong>
              </div>
            </section>
          </div>
        </main>
      </div>
    </div>
  );
}

export default Dashboard;
