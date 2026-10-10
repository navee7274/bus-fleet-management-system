import { Outlet } from "react-router-dom";
import Navbar from "./Navbar";
import Sidebar from "./Sidebar";
import "../styles/Driver.css";

function DriverLayout() {
  return (
    <div className="dashboard">
      <Sidebar />
      <div className="driver-layout-main">
        <Navbar />
        <Outlet />
      </div>
    </div>
  );
}

export default DriverLayout;
