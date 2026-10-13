import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import expenseService from "../../services/expenseService";
import Loading from "../../components/Loading";
import Button from "../../components/Button";
import Modal from "../../components/Modal";
import "../../styles/Expense.css";

function ExpenseList() {
  const navigate = useNavigate();

  const [expenses, setExpenses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [showDeleteModal, setShowDeleteModal] = useState(false);
  const [selectedExpense, setSelectedExpense] = useState(null);
  const [deleting, setDeleting] = useState(false);

  useEffect(() => {
    loadExpenses();
  }, []);

  const getExpenseId = (expense) => expense.expenceID ?? expense.ExpenceID;

  const getAmount = (value) => {
    const amount = value?.parsedValue ?? value;

    if (amount == null || amount === "") return 0;

    const parsed = Number(amount);
    return Number.isFinite(parsed) ? parsed : 0;
  };

  const formatCurrency = (value) =>
    getAmount(value).toLocaleString("en-LK", {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2,
    });

  const loadExpenses = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await expenseService.getAll();
      setExpenses(Array.isArray(data) ? data : []);
    } catch (error) {
      console.error("Failed to load expenses:", error);
      setError("Failed to load expenses.");
    } finally {
      setLoading(false);
    }
  };

  const confirmDelete = (expense) => {
    setSelectedExpense(expense);
    setShowDeleteModal(true);
  };

  const handleDelete = async () => {
    if (!selectedExpense || deleting) return;

    try {
      setDeleting(true);
      setError("");

      const id = getExpenseId(selectedExpense);

      await expenseService.delete(id);

      setExpenses((currentExpenses) => currentExpenses.filter((expense) => getExpenseId(expense) !== id));

      setShowDeleteModal(false);
      setSelectedExpense(null);
    } catch (error) {
      console.error("Failed to delete expense:", error);
      setError("Failed to delete expense.");
    } finally {
      setDeleting(false);
    }
  };

  if (loading) {
    return <Loading message="Loading expenses..." />;
  }

  return (
    <div className="expense-page">
      {/* Header */}
      <div className="page-header">
        <div>
          <h1>Expense Management</h1>
          <p>Manage your bus fleet expenses.</p>
        </div>

        <Button onClick={() => navigate("/expenses/add")}>+ Add Expense</Button>
      </div>

      {/* Error */}
      {error && (
        <div className="error-message">
          {error}
          <button type="button" onClick={loadExpenses} style={{ marginLeft: "12px" }}>
            Retry
          </button>
        </div>
      )}

      {/* Expenses Table */}
      <div className="expense-table-container">
        {expenses.length === 0 ? (
          <div className="empty-state">
            <h3>No expenses found</h3>
            <p>Add your first expense to get started.</p>
          </div>
        ) : (
          <table className="expense-table">
            <thead>
              <tr>
                <th>Expense ID</th>
                <th>Date</th>
                <th>Bus</th>
                <th>Category</th>
                <th>Payment Method</th>
                <th>Description</th>
                <th>Amount</th>
                <th>Actions</th>
              </tr>
            </thead>

            <tbody>
              {expenses.map((expense) => {
                const id = getExpenseId(expense);

                return (
                  <tr key={id}>
                    <td>
                      <strong>{id != null ? String(id).padStart(8, "0") : "-"}</strong>
                    </td>

                    <td>{expense.expencedate || "-"}</td>

                    <td>{expense.bus?.busRegistrationNo || "-"}</td>

                    <td>{expense.category || "-"}</td>

                    <td>{expense.paymentMethod || "-"}</td>

                    <td>{expense.expenceDescription || "-"}</td>

                    <td>Rs. {formatCurrency(expense.amount)}</td>

                    <td>
                      <div className="action-buttons">
                        <button type="button" className="action-view" onClick={() => navigate(`/expenses/${id}`)}>
                          View
                        </button>

                        <button type="button" className="action-edit" onClick={() => navigate(`/expenses/${id}/edit`)}>
                          Edit
                        </button>

                        <button type="button" className="action-delete" onClick={() => confirmDelete(expense)}>
                          Delete
                        </button>
                      </div>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        )}
      </div>

      {/* Delete Confirmation Modal */}
      <Modal
        isOpen={showDeleteModal}
        onClose={() => {
          if (!deleting) {
            setShowDeleteModal(false);
            setSelectedExpense(null);
          }
        }}
        title="Delete Expense"
      >
        <p>
          Are you sure you want to delete expense <strong>#{selectedExpense ? getExpenseId(selectedExpense) : ""}</strong>?
        </p>

        <p>This action cannot be undone.</p>

        <div className="modal-actions">
          <Button
            className="btn-secondary"
            onClick={() => {
              setShowDeleteModal(false);
              setSelectedExpense(null);
            }}
            disabled={deleting}
          >
            Cancel
          </Button>

          <Button className="btn-danger" onClick={handleDelete} disabled={deleting}>
            {deleting ? "Deleting..." : "Delete"}
          </Button>
        </div>
      </Modal>
    </div>
  );
}

export default ExpenseList;
