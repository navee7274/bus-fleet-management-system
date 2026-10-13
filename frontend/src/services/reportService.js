import api from "./api";

const reportService = {
  // 1. Bus Summary Report
  getBusSummary: async (startDate, endDate) => {
    const response = await api.get("/reports/bus-summary", {
      params: {
        startDate,
        endDate,
      },
    });
    return response.data;
  },

  // 2. Monthly Summary Report
  getMonthlySummary: async (year, month) => {
    const response = await api.get("/reports/monthly-summary", {
      params: { year, month },
    });
    return response.data;
  },

  // 3. Driver Salary Report
  getDriverSalary: async (year, month) => {
    const response = await api.get("/reports/driver-salary", {
      params: { year, month },
    });
    return response.data;
  },
};

export default reportService;
