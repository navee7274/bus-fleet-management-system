import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import expenseService from "../../services/expenseService";
import Loading from "../../components/Loading";
import Button from "../../components/Button";
import "../../styles/Expense.css";

function ExpenseDetails() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [expense, setExpense] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadExpense = async () => {
      try {
        setLoading(true);
        setError("");

        const data = await expenseService.getById(id);
        setExpense(data);
      } catch (err) {
        console.error("Failed to load expense:", err);
        setError("Failed to load expense.");
      } finally {
        setLoading(false);
      }
    };

    loadExpense();
  }, [id]);

  const getAmount = (value) => {
    const amount = value?.parsedValue ?? value?.source ?? value;

    if (amount == null || amount === "") return "-";

    const numericAmount = Number(amount);

    if (Number.isNaN(numericAmount)) return "-";

    return numericAmount.toLocaleString("en-LK", {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2,
    });
  };

  const formatDate = (date) => {
    if (!date) return "-";

    return date;
  };

  if (loading) {
    return <Loading />;
  }

  if (error) {
    return (
      <div className="expense-details-page">
        <div className="error-message">{error}</div>

        <Button onClick={() => navigate("/expenses")}>Back to Expenses</Button>
      </div>
    );
  }

  if (!expense) {
    return (
      <div className="expense-details-page">
        <div className="empty-state">
          <h3>Expense not found</h3>

          <Button onClick={() => navigate("/expenses")}>Back to Expenses</Button>
        </div>
      </div>
    );
  }

  const expenseId = expense.expenceID ?? id;
  const expenseDate = expense.expencedate;
  const category = expense.category ?? "-";
  const paymentMethod = expense.paymentMethod ?? "-";
  const description = expense.expenceDescription ?? "-";
  const busRegistrationNo = expense.bus?.busRegistrationNo ?? "-";
  const amount = expense.amount;

  return (
    <div className="expense-page">
      <div className="page-header">
        <div>
          <h1>Expense #{expenseId}</h1>
          <p>View expense information</p>
        </div>

        <Button onClick={() => navigate(`/expenses/${id}/edit`)}>Edit</Button>
      </div>

      <div className="expense-details-card">
        <div className="detail-item">
          <span>Expense ID</span>
          <strong>{expenseId}</strong>
        </div>

        <div className="detail-item">
          <span>Expense Date</span>
          <strong>{formatDate(expenseDate)}</strong>
        </div>

        <div className="detail-item">
          <span>Bus Registration No</span>
          <strong>{busRegistrationNo}</strong>
        </div>

        <div className="detail-item">
          <span>Category</span>
          <strong>{category}</strong>
        </div>

        <div className="detail-item">
          <span>Payment Method</span>
          <strong>{paymentMethod}</strong>
        </div>

        <div className="detail-item">
          <span>Expense Amount</span>
          <strong>{amount == null ? "-" : `Rs. ${getAmount(amount)}`}</strong>
        </div>

        <div className="detail-item detail-full">
          <span>Description</span>
          <strong>{description || "-"}</strong>
        </div>
      </div>

      <div style={{ marginTop: "20px" }}>
        <Button className="btn-secondary" onClick={() => navigate("/expenses")}>
          Back to Expenses
        </Button>
      </div>
    </div>
  );
}

export default ExpenseDetails;
