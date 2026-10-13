import { Outlet } from "react-router-dom";
import Navbar from "./Navbar";
import Sidebar from "./Sidebar";
import "../styles/Maintenance.css";

function MaintenanceLayout() {
  return (
    <div className="dashboard">
      <Sidebar />
      <div className="maintenance-layout-main">
        <Navbar />
        <Outlet />
      </div>
    </div>
  );
}

export default MaintenanceLayout;
