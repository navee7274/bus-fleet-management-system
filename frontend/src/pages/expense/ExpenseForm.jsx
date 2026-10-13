import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import expenseService from "../../services/expenseService";
import Button from "../../components/Button";
import Loading from "../../components/Loading";
import "../../styles/Expense.css";

const CATEGORIES = ["Insurance", "Tires", "Spare Parts", "Other Staff Wages", "Other"];

const PAYMENT_METHODS = ["Cash", "Bank Transfer", "Cheque", "Credit", "Other"];

function ExpenseForm() {
  const navigate = useNavigate();
  const { id } = useParams();

  const isEdit = Boolean(id);

  const [formData, setFormData] = useState({
    expenceID: "",
    expencedate: "",
    busRegistrationNo: "",
    category: "",
    paymentMethod: "",
    amount: "",
    expenceDescription: "",
  });

  const [loading, setLoading] = useState(false);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  /*
   * Load expense when editing
   */
  useEffect(() => {
    if (!isEdit) return;

    const loadExpense = async () => {
      try {
        setLoading(true);
        setError("");

        const data = await expenseService.getById(id);

        setFormData({
          expenceID: data.expenceID ?? "",
          expencedate: data.expencedate ?? "",
          busRegistrationNo: data.bus?.busRegistrationNo ?? "",
          category: data.category ?? "",
          paymentMethod: data.paymentMethod ?? "",
          amount: data.amount?.parsedValue ?? data.amount?.source ?? data.amount ?? "",
          expenceDescription: data.expenceDescription ?? "",
        });
      } catch (err) {
        console.error("Failed to load expense:", err);
        setError("Failed to load expense.");
      } finally {
        setLoading(false);
      }
    };

    loadExpense();
  }, [id, isEdit]);

  /*
   * Handle input changes
   */
  const handleChange = (e) => {
    const { name, value } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  /*
   * Submit form
   */
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    // Basic validation
    if (!formData.expencedate) {
      setError("Please select an expense date.");
      return;
    }

    if (!formData.busRegistrationNo.trim()) {
      setError("Please enter the bus registration number.");
      return;
    }

    if (!formData.category) {
      setError("Please select an expense category.");
      return;
    }

    if (!formData.paymentMethod) {
      setError("Please select a payment method.");
      return;
    }

    const amount = Number(formData.amount);

    if (!Number.isFinite(amount) || amount <= 0) {
      setError("Expense amount must be greater than zero.");
      return;
    }

    try {
      setSaving(true);

      /*
       * Request body matching the ExpenceLog entity.
       * The backend requires the bus as a nested object.
       */
      const expenseData = {
        expencedate: formData.expencedate,
        bus: {
          busRegistrationNo: formData.busRegistrationNo.trim(),
        },
        category: formData.category,
        paymentMethod: formData.paymentMethod,
        amount,
        expenceDescription: formData.expenceDescription.trim(),
      };

      if (isEdit) {
        await expenseService.update(id, expenseData);
      } else {
        await expenseService.create(expenseData);
      }

      navigate("/expenses");
    } catch (err) {
      console.error("Failed to save expense:", err);

      setError(
        err.response?.data?.message ||
          err.response?.data?.error ||
          (typeof err.response?.data === "string" ? err.response.data : null) ||
          "Failed to save expense.",
      );
    } finally {
      setSaving(false);
    }
  };

  if (loading) {
    return <Loading />;
  }

  return (
    <div className="expense-page">
      <div className="page-header">
        <div>
          <h1>{isEdit ? "Edit Expense" : "Add Expense"}</h1>
          <p>{isEdit ? "Update expense information" : "Enter the details for a new expense"}</p>
        </div>
      </div>

      <form className="expense-form" onSubmit={handleSubmit}>
        {error && <div className="error-message">{error}</div>}

        {/* Expense ID */}
        {isEdit && (
          <div className="form-group">
            <label>Expense ID</label>
            <input type="number" name="expenceID" value={formData.expenceID} disabled />
          </div>
        )}

        {/* Date + Bus */}
        <div className="form-row">
          <div className="form-group">
            <label htmlFor="expencedate">Expense Date</label>
            <input id="expencedate" type="date" name="expencedate" value={formData.expencedate} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label htmlFor="busRegistrationNo">Bus Registration No</label>
            <input
              id="busRegistrationNo"
              type="text"
              name="busRegistrationNo"
              value={formData.busRegistrationNo}
              onChange={handleChange}
              maxLength={20}
              placeholder="ND-4521"
              required
            />
          </div>
        </div>

        {/* Category + Payment Method */}
        <div className="form-row">
          <div className="form-group">
            <label htmlFor="category">Category</label>
            <select id="category" name="category" value={formData.category} onChange={handleChange} required>
              <option value="">Select category</option>
              {CATEGORIES.map((category) => (
                <option key={category} value={category}>
                  {category}
                </option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="paymentMethod">Payment Method</label>
            <select id="paymentMethod" name="paymentMethod" value={formData.paymentMethod} onChange={handleChange} required>
              <option value="">Select payment method</option>
              {PAYMENT_METHODS.map((method) => (
                <option key={method} value={method}>
                  {method}
                </option>
              ))}
            </select>
          </div>
        </div>

        {/* Amount */}
        <div className="form-group">
          <label htmlFor="amount">Expense Amount (LKR)</label>
          <input
            id="amount"
            type="number"
            name="amount"
            value={formData.amount}
            onChange={handleChange}
            min="0.01"
            step="0.01"
            placeholder="18500.00"
            required
          />
        </div>

        {/* Description */}
        <div className="form-group">
          <label htmlFor="expenceDescription">Description</label>
          <textarea
            id="expenceDescription"
            name="expenceDescription"
            value={formData.expenceDescription}
            onChange={handleChange}
            maxLength={15}
            rows={4}
            placeholder="Fuel expense"
          />
          <small>Maximum 15 characters, based on your current database column.</small>
        </div>

        {/* Buttons */}
        <div className="form-actions">
          <Button type="button" onClick={() => navigate("/expenses")}>
            Cancel
          </Button>

          <Button type="submit" disabled={saving}>
            {saving ? "Saving..." : isEdit ? "Update Expense" : "Create Expense"}
          </Button>
        </div>
      </form>
    </div>
  );
}

export default ExpenseForm;
