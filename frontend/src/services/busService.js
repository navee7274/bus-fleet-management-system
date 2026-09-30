import api from "./api";

const busService = {
  // Get all buses
  getAllBuses: async () => {
    const response = await api.get("/bus");
    return response.data;
  },

  // Get a single bus
  getBusByRegistrationNo: async (registrationNo) => {
    const response = await api.get(`/bus/${encodeURIComponent(registrationNo)}`);

    return response.data;
  },

  // Create a bus
  createBus: async (busData) => {
    const response = await api.post("/bus", busData);
    return response.data;
  },

  // Update a bus
  updateBus: async (registrationNo, busData) => {
    const response = await api.put(`/bus/${encodeURIComponent(registrationNo)}`, busData);

    return response.data;
  },

  // Deactivate a bus
  deactivateBus: async (registrationNo) => {
    const response = await api.put(`/bus/${encodeURIComponent(registrationNo)}/deactivate`);

    return response.data;
  },
};

export default busService;
