import { Outlet } from "react-router-dom";
import Navbar from "./Navbar";
import Sidebar from "./Sidebar";
import "../styles/Expense.css";

function ExpenseLayout() {
  return (
    <div className="dashboard">
      <Sidebar />
      <div className="expense-layout-main">
        <Navbar />
        <Outlet />
      </div>
    </div>
  );
}

export default ExpenseLayout;
