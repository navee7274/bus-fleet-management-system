import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import ProtectedRoute from "../context/ProtectedRoute";

import Login from "../pages/Login";
import CustomerBooking from "../pages/customer/CustomerBooking";

import Dashboard from "../pages/Dashboard";

import BusLayout from "../components/BusLayout";
import BusList from "../pages/buses/BusList";
import AddBus from "../pages/buses/AddBus";
import EditBus from "../pages/buses/EditBus";
import BusDetails from "../pages/buses/BusDetails";

import JourneyLayout from "../components/JourneyLayout";
import JourneyList from "../pages/journey/JourneyList";
import JourneyForm from "../pages/journey/JourneyForm";
import JourneyDetails from "../pages/journey/JourneyDetails";

import BookingLayout from "../components/BookingLayout";
import BookingList from "../pages/bookings/BookingList";
import ManageBooking from "../pages/bookings/ManageBooking";

import DriverLayout from "../components/DriverLayout";
import DriverList from "../pages/driver/DriverList";
import AddDriver from "../pages/driver/AddDriver";
import DriverDetails from "../pages/driver/DriverDetails";
import EditDriver from "../pages/driver/EditDriver";
import AddJourney from "../pages/journey/AddJourney";

import LandingPage from "../pages/LandingPage";
import TrackBooking from "../pages/customer/TrackBooking";

function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        {/* ================= PUBLIC ROUTES ================= */}

        <Route path="/login" element={<Login />} />

        <Route path="/book" element={<CustomerBooking />} />
        <Route path="/track" element={<TrackBooking />} />

        {/* ================ PROTECTED ROUTES ================ */}

        <Route element={<ProtectedRoute />}>
          <Route path="/dashboard" element={<Dashboard />} />

          <Route element={<BookingLayout />}>
            <Route path="/bookings" element={<BookingList />} />
            <Route path="/bookings/add" element={<CustomerBooking />} />
            <Route path="/bookings/:id/manage" element={<ManageBooking />} />
          </Route>

          <Route element={<JourneyLayout />}>
            <Route path="/journeys" element={<JourneyList />} />
            <Route path="/journeys/add" element={<AddJourney />} />
            <Route path="/journeys/:id" element={<JourneyDetails />} />
            <Route path="/journeys/:id/edit" element={<JourneyForm />} />
          </Route>

          {/* Bus Management */}
          <Route element={<BusLayout />}>
            <Route path="/buses" element={<BusList />} />
            <Route path="/buses/add" element={<AddBus />} />
            <Route path="/buses/:registrationNo" element={<BusDetails />} />
            <Route path="/buses/:registrationNo/edit" element={<EditBus />} />
          </Route>

          <Route element={<DriverLayout />}>
            <Route path="/drivers" element={<DriverList />} />
            <Route path="/drivers/add" element={<AddDriver />} />
            <Route path="/drivers/:DriverID" element={<DriverDetails />} />
            <Route path="/drivers/:DriverID/edit" element={<EditDriver />} />
          </Route>
        </Route>

        {/* ================= DEFAULT ================= */}

        <Route path="/" element={<LandingPage />} />

        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default AppRoutes;
