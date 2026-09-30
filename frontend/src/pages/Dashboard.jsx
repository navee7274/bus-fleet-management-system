import { useAuth } from "../context/AuthContext";
import Navbar from "../components/Navbar";
import Sidebar from "../components/Sidebar";
import "../styles/Dashboard.css";

function Dashboard() {
  const { user } = useAuth();

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
                <h2>24</h2>
              </div>
            </div>

            <div className="stat-card">
              <div className="stat-icon">📋</div>
              <div>
                <span>Active Bookings</span>
                <h2>18</h2>
              </div>
            </div>

            <div className="stat-card">
              <div className="stat-icon">🛣️</div>
              <div>
                <span>Today's Journeys</span>
                <h2>12</h2>
              </div>
            </div>

            <div className="stat-card">
              <div className="stat-icon">💰</div>
              <div>
                <span>Monthly Revenue</span>
                <h2>Rs. 485,000</h2>
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

                <button className="view-button">View All</button>
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
                    <tr>
                      <td>#BK-1024</td>
                      <td>Kasun Perera</td>
                      <td>Colombo → Kandy</td>
                      <td>
                        <span className="status confirmed">Confirmed</span>
                      </td>
                    </tr>

                    <tr>
                      <td>#BK-1023</td>
                      <td>Nimal Silva</td>
                      <td>Galle → Colombo</td>
                      <td>
                        <span className="status pending">Pending</span>
                      </td>
                    </tr>

                    <tr>
                      <td>#BK-1022</td>
                      <td>Amal Fernando</td>
                      <td>Matara → Galle</td>
                      <td>
                        <span className="status confirmed">Confirmed</span>
                      </td>
                    </tr>

                    <tr>
                      <td>#BK-1021</td>
                      <td>Sahan Perera</td>
                      <td>Colombo → Jaffna</td>
                      <td>
                        <span className="status cancelled">Cancelled</span>
                      </td>
                    </tr>
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
                    Available
                  </div>

                  <strong>14</strong>
                </div>

                <div className="fleet-item">
                  <div className="fleet-label">
                    <span className="fleet-dot active"></span>
                    On Journey
                  </div>

                  <strong>7</strong>
                </div>

                <div className="fleet-item">
                  <div className="fleet-label">
                    <span className="fleet-dot maintenance"></span>
                    Maintenance
                  </div>

                  <strong>3</strong>
                </div>
              </div>

              <div className="fleet-total">
                <span>Total Fleet</span>
                <strong>24 Buses</strong>
              </div>
            </section>
          </div>

          {/* Bottom Grid */}
          <div className="dashboard-grid bottom-grid">
            {/* Today's Journeys */}
            <section className="dashboard-card">
              <div className="card-header">
                <div>
                  <h2>Today's Journeys</h2>
                  <p>Upcoming scheduled journeys</p>
                </div>
              </div>

              <div className="journey-list">
                <div className="journey">
                  <div className="journey-time">
                    <strong>08:30</strong>
                    <span>AM</span>
                  </div>

                  <div className="journey-route">
                    <strong>Colombo → Kandy</strong>
                    <span>Bus WP-1234</span>
                  </div>

                  <span className="status confirmed">Active</span>
                </div>

                <div className="journey">
                  <div className="journey-time">
                    <strong>11:00</strong>
                    <span>AM</span>
                  </div>

                  <div className="journey-route">
                    <strong>Galle → Colombo</strong>
                    <span>Bus SP-4567</span>
                  </div>

                  <span className="status pending">Upcoming</span>
                </div>

                <div className="journey">
                  <div className="journey-time">
                    <strong>02:30</strong>
                    <span>PM</span>
                  </div>

                  <div className="journey-route">
                    <strong>Colombo → Jaffna</strong>
                    <span>Bus NC-7890</span>
                  </div>

                  <span className="status pending">Upcoming</span>
                </div>
              </div>
            </section>

            {/* Maintenance */}
            <section className="dashboard-card">
              <div className="card-header">
                <div>
                  <h2>Maintenance</h2>
                  <p>Buses requiring attention</p>
                </div>
              </div>

              <div className="maintenance-summary">
                <div className="maintenance-number">
                  <strong>3</strong>
                  <span>Buses in maintenance</span>
                </div>

                <div className="maintenance-number">
                  <strong>2</strong>
                  <span>Due this week</span>
                </div>
              </div>

              <button className="maintenance-button">View Maintenance</button>
            </section>
          </div>
        </main>
      </div>
    </div>
  );
}

export default Dashboard;
