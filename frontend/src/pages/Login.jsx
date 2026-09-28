import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import api from "../services/api";
import "../styles/Login.css";
import logo from "../assets/logo.svg";
function Login() {
  const navigate = useNavigate();
  const { login } = useAuth();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const handleSubmit = async (event) => {
    event.preventDefault();
    setError("");
    try {
      const response = await api.post("/auth/login", { username, password });
      login(response.data);
      navigate("/dashboard");
    } catch (error) {
      setError("Invalid username or password.");
    }
  };
  return (
    <div className="login-page">
      {" "}
      <div className="login-card">
        {" "}
        <div className="login-logo">
          {" "}
          <img src={logo} alt="Bus Fleet Management" />{" "}
        </div>{" "}
        <h1>Welcome Back</h1> <p className="login-subtitle"> Sign in to manage your operations </p>{" "}
        <form onSubmit={handleSubmit}>
          {" "}
          <div className="form-group">
            {" "}
            <label htmlFor="username"> Username </label>{" "}
            <input
              id="username"
              type="text"
              placeholder="Enter your username"
              value={username}
              onChange={(event) => setUsername(event.target.value)}
              required
            />{" "}
          </div>{" "}
          <div className="form-group">
            {" "}
            <label htmlFor="password"> Password </label>{" "}
            <input
              id="password"
              type="password"
              placeholder="Enter your password"
              value={password}
              onChange={(event) => setPassword(event.target.value)}
              required
            />{" "}
          </div>{" "}
          {error && <p className="login-error"> {error} </p>}{" "}
          <button type="submit" className="login-button">
            {" "}
            Sign In{" "}
          </button>{" "}
        </form>{" "}
      </div>{" "}
    </div>
  );
}
export default Login;
