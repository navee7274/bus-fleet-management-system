import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Button from "../../components/Button";
import expenseService from "../../services/expenseService";
import "../../styles/Expense.css";

function AddExpense() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    expencedate: "",
    busRegistrationNo: "",
    category: "",
    paymentMethod: "",
    amount: "",
    expenceDescription: "",
  });

  const [error, setError] = useState("");
  const [saving, setSaving] = useState(false);

  const categories = ["Insurance", "Tires", "Spare Parts", "Other Staff Wages", "Other"];

  const paymentMethods = ["Cash", "Bank Transfer", "Cheque", "Credit", "Other"];

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((current) => ({
      ...current,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      setSaving(true);
      setError("");

      const expenseData = {
        expencedate: formData.expencedate,
        bus: {
          busRegistrationNo: formData.busRegistrationNo.trim(),
        },
        category: formData.category,
        paymentMethod: formData.paymentMethod,
        amount: Number(formData.amount),
        expenceDescription: formData.expenceDescription.trim(),
      };

      await expenseService.create(expenseData);

      navigate("/expenses");
    } catch (error) {
      console.error(error);

      setError(error.response?.data?.message || (typeof error.response?.data === "string" ? error.response.data : "") || "Failed to add expense.");
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="expense-page">
      <div className="page-header">
        <div>
          <h1>Add an Expense</h1>
          <p>Enter your bus fleet expense details below.</p>
        </div>
      </div>

      <form className="expense-form" onSubmit={handleSubmit}>
        {error && <div className="error-message">{String(error)}</div>}

        {/* Expense Date + Bus */}
        <div className="form-row">
          <div className="form-group">
            <label>Expense Date</label>

            <input type="date" name="expencedate" value={formData.expencedate} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label>Bus Registration No</label>

            <input type="text" name="busRegistrationNo" value={formData.busRegistrationNo} onChange={handleChange} placeholder="ND-4521" required />
          </div>
        </div>

        {/* Category + Payment Method */}
        <div className="form-row">
          <div className="form-group">
            <label>Category</label>

            <select name="category" value={formData.category} onChange={handleChange} required>
              <option value="">Select a category</option>

              {categories.map((category) => (
                <option key={category} value={category}>
                  {category}
                </option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label>Payment Method</label>

            <select name="paymentMethod" value={formData.paymentMethod} onChange={handleChange} required>
              <option value="">Select payment method</option>

              {paymentMethods.map((method) => (
                <option key={method} value={method}>
                  {method}
                </option>
              ))}
            </select>
          </div>
        </div>

        {/* Amount */}
        <div className="form-group">
          <label>Expense Amount (Rs.)</label>

          <input type="number" name="amount" value={formData.amount} onChange={handleChange} min="0.01" step="0.01" placeholder="18500.00" required />
        </div>

        {/* Description */}
        <div className="form-group">
          <label>Description</label>

          <textarea
            name="expenceDescription"
            value={formData.expenceDescription}
            onChange={handleChange}
            maxLength={15}
            rows="4"
            placeholder="Bus insurance"
          />
        </div>

        {/* Buttons */}
        <div className="form-actions">
          <Button type="button" onClick={() => navigate("/expenses")} disabled={saving}>
            Cancel
          </Button>

          <Button type="submit" disabled={saving}>
            {saving ? "Saving..." : "Create Expense"}
          </Button>
        </div>
      </form>
    </div>
  );
}

export default AddExpense;
