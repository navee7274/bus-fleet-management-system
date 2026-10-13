import api from "./api";

const maintenanceLogService = {
  // Get all maintenance logs
  getAll: async () => {
    const response = await api.get("/maintenance-log");
    return response.data;
  },

  // Get maintenance log by ID
  getById: async (id) => {
    const response = await api.get(`/maintenance-log/${id}`);
    return response.data;
  },

  // Create maintenance log
  create: async (maintenanceLog) => {
    const response = await api.post("/maintenance-log", maintenanceLog);
    return response.data;
  },

  // Update maintenance log
  update: async (id, maintenanceLog) => {
    const response = await api.put(`/maintenance-log/${id}`, maintenanceLog);
    return response.data;
  },

  // Delete maintenance log
  delete: async (id) => {
    await api.delete(`/maintenance-log/${id}`);
  },

  // Get maintenance logs by bus registration number
  getByBus: async (registrationNo) => {
    const response = await api.get(`/maintenance-log/bus/${encodeURIComponent(registrationNo)}`);
    return response.data;
  },

  // Get maintenance logs by type
  getByType: async (type) => {
    const response = await api.get(`/maintenance-log/type/${encodeURIComponent(type)}`);
    return response.data;
  },

  // Get maintenance logs by date
  getByDate: async (date) => {
    const response = await api.get(`/maintenance-log/date/${date}`);
    return response.data;
  },

  // Get maintenance logs between dates
  getBetweenDates: async (startDate, endDate) => {
    const response = await api.get("/maintenance-log/date-range", {
      params: { startDate, endDate },
    });
    return response.data;
  },

  // Get total maintenance cost between dates
  getTotalCost: async (startDate, endDate) => {
    const response = await api.get("/maintenance-log/total-cost", {
      params: { startDate, endDate },
    });
    return response.data;
  },
};

export default maintenanceLogService;
