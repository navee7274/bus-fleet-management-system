import api from "./api";

const expenseService = {
  getAll: async () => {
    const response = await api.get("/expenses");
    return response.data;
  },

  getById: async (id) => {
    const response = await api.get(`/expenses/${id}`);
    return response.data;
  },

  create: async (expense) => {
    const response = await api.post("/expenses", expense);
    return response.data;
  },

  update: async (id, expense) => {
    const response = await api.put(`/expenses/${id}`, expense);
    return response.data;
  },

  delete: async (id) => {
    await api.delete(`/expenses/${id}`);
  },
};

export default expenseService;
