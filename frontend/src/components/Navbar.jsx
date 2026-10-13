import { useAuth } from "../context/AuthContext";

function Navbar() {
  const { user, logout } = useAuth();

  return (
    <nav className="navbar">
      {" "}
      <div className="navbar-brand">Management Interface</div>
      <div className="navbar-user">
        {user && (
          <>
            <span>{user.name}</span>

            <button onClick={logout}>Logout</button>
          </>
        )}
      </div>
    </nav>
  );
}

export default Navbar;
