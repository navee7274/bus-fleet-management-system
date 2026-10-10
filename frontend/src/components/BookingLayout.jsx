import { Outlet } from "react-router-dom";
import Navbar from "./Navbar";
import Sidebar from "./Sidebar";
import "../styles/Booking.css";

function BookingLayout() {
  return (
    <div className="dashboard">
      <Sidebar />
      <div className="booking-layout-main">
        <Navbar />
        <Outlet />
      </div>
    </div>
  );
}

export default BookingLayout;
