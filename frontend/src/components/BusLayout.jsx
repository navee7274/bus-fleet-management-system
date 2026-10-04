import { Outlet } from "react-router-dom";
import Navbar from "./Navbar";
import Sidebar from "./Sidebar";
import "../styles/Bus.css";

function BusLayout() {
  return (
    <div className="dashboard">
      <Sidebar />
      <div className="bus-layout-main">
        <Navbar />
        <Outlet />
      </div>
    </div>
  );
}

export default BusLayout;