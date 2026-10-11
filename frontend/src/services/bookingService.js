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

  approveBooking: async (id, paymentMethod, driverID, advanceAmount, finalPrice) => {
    const response = await api.post(`/bookings/${id}/approve`, null, {
      params: {
        paymentMethod,
        driverID,
        advanceAmount,
        finalPrice,
      },
    });

    return response.data;
  },

  confirmBooking: async (id) => {
    const response = await api.post(`/bookings/${id}/confirm`);
    return response.data;
  },

  cancelBooking: async (id) => {
    const response = await api.get(`/bookings/${id}/cancel`);
    return response.data;
  },

  completeBooking: async (id, startOdo, endOdo) => {
    const response = await api.post(`/bookings/${id}/complete`, null, {
      params: {
        startOdo,
        endOdo,
      },
    });

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

  getAvailableDrivers: (availabilityRequest) => {
    return api.post("/availability/drivers", availabilityRequest);
  },

  createBooking: (bookingData) => {
    return api.post("/bookings", bookingData);
  },

  getPaymentData: async (bookingId) => {
    const response = await api.get(`/payments/${bookingId}`);
    return response.data;
  },

  getPaymentMethod: async (bookingId) => {
    const response = await api.get(`/payments/${bookingId}/paymentmethod`);
    return response.data;
  },

  markPaymentDone: async (bookingId) => {
    const response = await api.post(`/payments/${bookingId}/mark`);
    return response.data;
  },
};

export default bookingService;
