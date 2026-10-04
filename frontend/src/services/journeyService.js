import api from "./api";

const journeyService = {
  getAllJourneys: async () => {
    const response = await api.get("/journeys");
    return response.data;
  },

  getById: async (id) => {
    const response = await api.get(`/journeys/${id}`);
    return response.data;
  },

  create: async (journey) => {
    const response = await api.post("/journeys", journey);
    return response.data;
  },

  update: async (id, journey) => {
    const response = await api.put(`/journeys/${id}`, journey);
    return response.data;
  },

  deleteJourney: async (id) => {
    await api.delete(`/journeys/${id}`);
  },
};

export default journeyService;
