import { Link } from "react-router-dom";

import logoWhite from "../assets/logo-white.svg";

function CustomerFooter() {
  const handleSectionLink = (sectionId) => (event) => {
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
    <footer className="footer" id="contact">
      <div className="footer-content">
        <div className="footer-brand">
          <Link to="/" className="navbar-brand">
            <img src={logoWhite} alt="Tharusha Tours logo" />
          </Link>

          <p>Making every journey comfortable, convenient, and memorable.</p>
        </div>

        <div className="footer-links">
          <h3>Explore</h3>
          <Link to="/#home" onClick={handleSectionLink("home")}>
            Home
          </Link>
          <Link to="/#services" onClick={handleSectionLink("services")}>
            Services
          </Link>
          <Link to="/#fleet" onClick={handleSectionLink("fleet")}>
            Our Fleet
          </Link>
        </div>

        <div className="footer-links">
          <h3>Contact</h3>
          <a href="tel:+94770000000">+94 77 000 0000</a>
          <a href="mailto:info@tharushatours.com">info@tharushatours.com</a>
          <Link to="/login">Admin Portal</Link>
        </div>
      </div>

      <div className="footer-bottom">
        <p>© {new Date().getFullYear()} Tharusha Tours. All rights reserved.</p>
        <span>Made for better journeys in Sri Lanka.</span>
      </div>
    </footer>
  );
}

export default CustomerFooter;
