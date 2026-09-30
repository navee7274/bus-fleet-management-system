import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import Login from "../pages/Login";
import CreateBooking from "../pages/bookings/CreateBooking";
import Dashboard from "../pages/Dashboard";

import BusList from "../pages/buses/BusList";
import AddBus from "../pages/buses/AddBus";
import EditBus from "../pages/buses/EditBus";
import BusDetails from "../pages/buses/BusDetails";
import BusLayout from "../components/BusLayout";

import CustomerBooking from "../pages/customer/CustomerBooking";

import ProtectedRoute from "../context/ProtectedRoute";

function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        {/* ================= PUBLIC ROUTES ================= */}

        <Route path="/login" element={<Login />} />
        <Route path="/customer/book" element={<CustomerBooking />} />

        {/* ================= PROTECTED ROUTES ================= */}

        <Route element={<ProtectedRoute />}>
          <Route path="/dashboard" element={<Dashboard />} />

          <Route path="/book" element={<CreateBooking />} />

          {/* Bus Management */}
          <Route element={<BusLayout />}>
            <Route path="/buses" element={<BusList />} />
            <Route path="/buses/add" element={<AddBus />} />
            <Route path="/buses/:registrationNo" element={<BusDetails />} />
            <Route path="/buses/:registrationNo/edit" element={<EditBus />} />
          </Route>
        </Route>

        {/* ================= DEFAULT ================= */}

        <Route path="/" element={<Navigate to="/dashboard" replace />} />

        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default AppRoutes;
