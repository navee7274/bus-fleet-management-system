import api from "./api";

const bookingService = {
  // ADMIN BOOKING SERVICE METHODS

  getAllBookings: async () => {
    const response = await api.get("/bookings");
    return response.data;
  },

  getById: async (id) => {
    const response = await api.get(`/bookings/${id}`);
    return response.data;
  },

  rejectBooking: async (id) => {
    const response = await api.get(`/bookings/${id}/reject`);
    return response.data;
  },

  approveBooking: async (id) => {
    const response = await api.get(`/bookings/${id}/approve`);
    return response.data;
  },

  confirmBooking: async (id) => {
    const response = await api.get(`/bookings/${id}/confirm`);
    return response.data;
  },

  cancelBooking: async (id) => {
    const response = await api.get(`/bookings/${id}/cancel`);
    return response.data;
  },

  completeBooking: async (id) => {
    const response = await api.get(`/bookings/${id}/complete`);
    return response.data;
  },

  update: async (id, booking) => {
    const response = await api.put(`/bookings/${id}`, booking);
    return response.data;
  },

  // CUSTOMER BOOKING SERVICE METHODS

  getAvailableBuses: (availabilityRequest) => {
    return api.post("/availability/buses", availabilityRequest);
  },

  createBooking: (bookingData) => {
    return api.post("/bookings", bookingData);
  },
};

export default bookingService;
