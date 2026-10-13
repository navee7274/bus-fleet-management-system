import api from "./api";

const fuelService = {
  getAll: async () => {
    const response = await api.get("/fuel-log");
    return response.data;
  },

  getById: async (id) => {
    const response = await api.get(`/fuel-log/${id}`);
    return response.data;
  },

  create: async (journey) => {
    const response = await api.post("/fuel-log", journey);
    return response.data;
  },

  update: async (id, journey) => {
    const response = await api.put(`/fuel-log/${id}`, journey);
    return response.data;
  },

  delete: async (id) => {
    await api.delete(`/fuel-log/${id}`);
  },
};

export default fuelService;
