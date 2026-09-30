import { Outlet } from "react-router-dom";
import Navbar from "./Navbar";
import Sidebar from "./Sidebar";
import "../styles/Journey.css";

function JourneyLayout() {
  return (
    <div className="dashboard">
      <Sidebar />

      <div className="journey-layout-main">
        <Navbar />
        <Outlet />
      </div>
    </div>
  );
}

export default JourneyLayout;
