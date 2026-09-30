import api from "./api";

const bookingService = {
  getAvailableBuses: (availabilityRequest) => {
    return api.post("/availability/buses", availabilityRequest);
  },

  createBooking: (bookingData) => {
    return api.post("/bookings", bookingData);
  },
};

export default bookingService;
