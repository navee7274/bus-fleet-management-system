import { useState } from "react";
import { Link } from "react-router-dom";

import logo from "../assets/logo.svg";

function CustomerNavbar() {
  const [menuOpen, setMenuOpen] = useState(false);

  const handleSectionLink = (sectionId) => (event) => {
    setMenuOpen(false);

    if (window.location.pathname === "/") {
      event.preventDefault();
      const section = document.getElementById(sectionId);

      if (section) {
        section.scrollIntoView({ behavior: "smooth", block: "start" });
        window.history.pushState(null, "", `/#${sectionId}`);
      } else {
        window.location.href = `/#${sectionId}`;
      }
    }
  };

  return (
    <nav className="navbar cp">
      <Link to="/" className="navbar-brand">
        <img src={logo} alt="Tharusha Tours logo" />
      </Link>

      <button className="menu-toggle" onClick={() => setMenuOpen(!menuOpen)} aria-label="Toggle navigation" aria-expanded={menuOpen}>
        {menuOpen ? "✕" : "☰"}
      </button>

      <div className={`nav-links ${menuOpen ? "open" : ""}`}>
        <Link to="/#home" onClick={handleSectionLink("home")}>
          Home
        </Link>
        <Link to="/#services" onClick={handleSectionLink("services")}>
          Services
        </Link>
        <Link to="/#fleet" onClick={handleSectionLink("fleet")}>
          Our Fleet
        </Link>

        <Link to="/track" className="nav-login" onClick={() => setMenuOpen(false)}>
          Track Booking
        </Link>
      </div>
    </nav>
  );
}

export default CustomerNavbar;
