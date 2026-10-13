import { Outlet } from "react-router-dom";
import Navbar from "./Navbar";
import Sidebar from "./Sidebar";
import "../styles/Fuel.css";

function FuelLayout() {
  return (
    <div className="dashboard">
      <Sidebar />
      <div className="fuel-layout-main">
        <Navbar />
        <Outlet />
      </div>
    </div>
  );
}

export default FuelLayout;
