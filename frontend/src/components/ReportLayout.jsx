import { Outlet } from "react-router-dom";
import Navbar from "./Navbar";
import Sidebar from "./Sidebar";
import "../styles/Report.css";

function ReportLayout() {
  return (
    <div className="dashboard">
      <Sidebar />
      <div className="report-layout-main">
        <Navbar />
        <Outlet />
      </div>
    </div>
  );
}

export default ReportLayout;
