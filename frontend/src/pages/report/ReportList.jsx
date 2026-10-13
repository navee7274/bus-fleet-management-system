import { useEffect, useState } from "react";
import reportService from "../../services/reportService";
import Loading from "../../components/Loading";
import Button from "../../components/Button";
import "../../styles/Report.css";

function ReportList() {
  const today = new Date();
  const [reportType, setReportType] = useState("bus");

  const [startDate, setStartDate] = useState(`${today.getFullYear()}-${String(today.getMonth() + 1).padStart(2, "0")}-01`);
  const [endDate, setEndDate] = useState(
    `${today.getFullYear()}-${String(today.getMonth() + 1).padStart(2, "0")}-${String(today.getDate()).padStart(2, "0")}`,
  );

  const [year, setYear] = useState(today.getFullYear());
  const [month, setMonth] = useState(today.getMonth() + 1);

  const [reportData, setReportData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [generated, setGenerated] = useState(false);

  const formatMoney = (value) =>
    `Rs. ${Number(value ?? 0).toLocaleString("en-LK", {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2,
    })}`;

  const formatNumber = (value) =>
    Number(value ?? 0).toLocaleString("en-LK", {
      maximumFractionDigits: 2,
    });

  const loadReport = async () => {
    try {
      setLoading(true);
      setError("");
      setGenerated(false);

      let data;

      if (reportType === "bus") {
        if (startDate && endDate && startDate > endDate) {
          setError("Start date cannot be after end date.");
          return;
        }

        data = await reportService.getBusSummary(startDate || undefined, endDate || undefined);
      } else if (reportType === "monthly") {
        data = await reportService.getMonthlySummary(Number(year), Number(month));
      } else {
        data = await reportService.getDriverSalary(Number(year), Number(month));
      }

      setReportData(Array.isArray(data) ? data : []);
      setGenerated(true);
    } catch (err) {
      console.error(err);
      setError(err.response?.data?.message || "Failed to generate report.");
      setReportData([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadReport();
  }, []);

  const total = (key) => reportData.reduce((sum, item) => sum + Number(item[key] ?? 0), 0);

  const totalIncome = total("income");
  const totalCost = reportType === "bus" ? total("totalCost") : reportType === "monthly" ? total("totalCost") : total("monthlySalary");

  const totalProfit = reportType === "driver" ? 0 : total("netProfit");

  const exportCSV = () => {
    if (!reportData.length) return;

    const headers = Object.keys(reportData[0]);
    const escapeCSV = (value) => {
      const text = String(value ?? "");
      return `"${text.replace(/"/g, '""')}"`;
    };

    const csv = [headers.map(escapeCSV).join(","), ...reportData.map((row) => headers.map((header) => escapeCSV(row[header])).join(","))].join(
      "\r\n",
    );

    const blob = new Blob(["\uFEFF" + csv], {
      type: "text/csv;charset=utf-8;",
    });

    const url = URL.createObjectURL(blob);
    const link = document.createElement("a");
    link.href = url;
    link.download = `${reportType}-report.csv`;
    link.click();
    URL.revokeObjectURL(url);
  };

  return (
    <div className="report-page">
      <div className="page-header">
        <div>
          <h1>Reports Management</h1>
          <p>Analyze fleet performance, finances, and driver salaries.</p>
        </div>

        <Button onClick={exportCSV} disabled={!generated || reportData.length === 0}>
          Export CSV
        </Button>
      </div>

      <div className="report-filters">
        <div className="report-field">
          <label>Report Type</label>
          <select value={reportType} onChange={(e) => setReportType(e.target.value)}>
            <option value="bus">Bus Summary</option>
            <option value="monthly">Monthly Summary</option>
            <option value="driver">Driver Salary</option>
          </select>
        </div>

        {reportType === "bus" ? (
          <>
            <div className="report-field">
              <label>Start Date</label>
              <input type="date" value={startDate} onChange={(e) => setStartDate(e.target.value)} max={endDate || undefined} />
            </div>

            <div className="report-field">
              <label>End Date</label>
              <input type="date" value={endDate} onChange={(e) => setEndDate(e.target.value)} min={startDate || undefined} />
            </div>
          </>
        ) : (
          <>
            <div className="report-field">
              <label>Year</label>
              <input type="number" min="2000" max="2100" value={year} onChange={(e) => setYear(e.target.value)} />
            </div>

            <div className="report-field">
              <label>Month</label>
              <select value={month} onChange={(e) => setMonth(e.target.value)}>
                {Array.from({ length: 12 }, (_, i) => (
                  <option key={i + 1} value={i + 1}>
                    {new Date(2000, i).toLocaleString("en", {
                      month: "long",
                    })}
                  </option>
                ))}
              </select>
            </div>
          </>
        )}

        <Button onClick={loadReport} disabled={loading}>
          {loading ? "Generating..." : "Generate Report"}
        </Button>
      </div>

      {error && <div className="error-message">{error}</div>}

      {loading ? (
        <Loading message="Generating report..." />
      ) : generated ? (
        <>
          <div className="report-summary">
            <div className="report-card">
              <span>{reportType === "driver" ? "Drivers" : reportType === "monthly" ? "Buses" : "Buses"}</span>
              <h2>{reportData.length}</h2>
            </div>

            {reportType !== "driver" && (
              <>
                <div className="report-card">
                  <span>Total Income</span>
                  <h2>{formatMoney(totalIncome)}</h2>
                </div>

                <div className="report-card">
                  <span>Total Costs</span>
                  <h2>{formatMoney(totalCost)}</h2>
                </div>

                <div className="report-card">
                  <span>Net Profit</span>
                  <h2 className={totalProfit < 0 ? "negative" : "positive"}>{formatMoney(totalProfit)}</h2>
                </div>
              </>
            )}

            {reportType === "driver" && (
              <div className="report-card">
                <span>Total Monthly Salaries</span>
                <h2>{formatMoney(totalCost)}</h2>
              </div>
            )}
          </div>

          <div className="report-table-container">
            {reportData.length === 0 ? (
              <div className="empty-state">
                <h3>No report data found</h3>
                <p>Try a different date range or month.</p>
              </div>
            ) : (
              <table className="report-table">
                <thead>
                  {reportType === "bus" ? (
                    <tr>
                      <th>Bus</th>
                      <th>Income</th>
                      <th>Fuel Cost</th>
                      <th>Maintenance</th>
                      <th>Other Expenses</th>
                      <th>Total Cost</th>
                      <th>Net Profit</th>
                      <th>Distance</th>
                      <th>Fuel (L)</th>
                      <th>KM/L</th>
                    </tr>
                  ) : reportType === "monthly" ? (
                    <tr>
                      <th>Year</th>
                      <th>Month</th>
                      <th>Bus</th>
                      <th>Income</th>
                      <th>Total Cost</th>
                      <th>Net Profit</th>
                    </tr>
                  ) : (
                    <tr>
                      <th>Driver ID</th>
                      <th>Driver</th>
                      <th>Month</th>
                      <th>Base Salary</th>
                      <th>Trips</th>
                      <th>Trip Allowance</th>
                      <th>Monthly Salary</th>
                    </tr>
                  )}
                </thead>

                <tbody>
                  {reportData.map((item, index) => (
                    <tr key={item.busId ?? item.registrationNo ?? item.driverID ?? `${index}`}>
                      {reportType === "bus" ? (
                        <>
                          <td>
                            <strong>{item.registrationNo || "-"}</strong>
                          </td>
                          <td>{formatMoney(item.income)}</td>
                          <td>{formatMoney(item.fuelCost)}</td>
                          <td>{formatMoney(item.maintenanceCost)}</td>
                          <td>{formatMoney(item.otherExpenses)}</td>
                          <td>{formatMoney(item.totalCost)}</td>
                          <td className={Number(item.netProfit) < 0 ? "negative" : "positive"}>{formatMoney(item.netProfit)}</td>
                          <td>{formatNumber(item.totalKm)} km</td>
                          <td>{formatNumber(item.fuelLitres)}</td>
                          <td>{formatNumber(item.fuelEconomyKmPerLitre)}</td>
                        </>
                      ) : reportType === "monthly" ? (
                        <>
                          <td>{item.year}</td>
                          <td>
                            {new Date(Number(item.year), Number(item.month) - 1).toLocaleString("en", {
                              month: "short",
                            })}
                          </td>
                          <td>
                            <strong>{item.registrationNo || "-"}</strong>
                          </td>
                          <td>{formatMoney(item.income)}</td>
                          <td>{formatMoney(item.totalCost)}</td>
                          <td className={Number(item.netProfit) < 0 ? "negative" : "positive"}>{formatMoney(item.netProfit)}</td>
                        </>
                      ) : (
                        <>
                          <td>{item.driverID ?? "-"}</td>
                          <td>
                            <strong>{item.driverName || "-"}</strong>
                          </td>
                          <td>
                            {new Date(2000, Number(item.month) - 1).toLocaleString("en", {
                              month: "short",
                            })}
                          </td>
                          <td>{formatMoney(item.baseSalary)}</td>
                          <td>{formatNumber(item.tripCount)}</td>
                          <td>{formatMoney(item.tripAllowance)}</td>
                          <td>
                            <strong>{formatMoney(item.monthlySalary)}</strong>
                          </td>
                        </>
                      )}
                    </tr>
                  ))}
                </tbody>
              </table>
            )}
          </div>
        </>
      ) : null}
    </div>
  );
}

export default ReportList;
