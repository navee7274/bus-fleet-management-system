import { NavLink } from "react-router-dom";

function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="sidebar-logo">Bus Fleet</div>
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
