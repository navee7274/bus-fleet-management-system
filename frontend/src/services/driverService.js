import api from "./api";

const driverService = {
  getAllDrivers: async () => {
    const response = await api.get("/driver");
    return response.data;
  },

  getById: async (driverID) => {
    const response = await api.get(`/driver/${encodeURIComponent(driverID)}`);

    return response.data;
  },

  createDriver: async (driverData) => {
    const response = await api.post("/driver", driverData);
    return response.data;
  },

  updateDriver: async (driverID, driverData) => {
    const response = await api.put(`/driver/${encodeURIComponent(driverID)}`, driverData);
    return response.data;
  },

  deactivateDriver: async (driverID) => {
    const response = await api.put(`/driver/${encodeURIComponent(driverID)}/deactivate`);
    return response.data;
  },
};

export default driverService;
