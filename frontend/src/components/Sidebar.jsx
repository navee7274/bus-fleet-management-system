import { NavLink } from "react-router-dom";
import logo from "../assets/logo-white.svg";

function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="sidebar-logo">
        <img src={logo} alt="Tharusha Tours logo" />
      </div>
      <nav>
        <NavLink to="/dashboard">Dashboard</NavLink>

        <NavLink to="/buses">Buses</NavLink>

        <NavLink to="/drivers">Drivers</NavLink>

        <NavLink to="/bookings">Bookings</NavLink>

        <NavLink to="/journeys">Journeys</NavLink>

        <NavLink to="/maintenance">Maintenance</NavLink>

        <NavLink to="/fuel">Fuel</NavLink>

        <NavLink to="/expenses">Expenses</NavLink>

        <NavLink to="/reports">Reports</NavLink>
      </nav>
    </aside>
  );
}

export default Sidebar;
