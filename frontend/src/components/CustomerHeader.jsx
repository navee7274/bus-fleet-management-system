import { Link } from "react-router-dom";

function CustomerHeader() {
  return (
    <header className="hero" id="home">
      <div className="hero-overlay">
        <div className="hero-content">
          <span className="hero-tag">YOUR JOURNEY, OUR PRIORITY</span>

          <h1>
            Travel Further.
            <br />
            <span>Travel Better.</span>
          </h1>

          <p>From family trips to corporate outings, discover reliable bus hire and comfortable journeys with Tharusha Tours.</p>

          <div className="hero-actions">
            <Link to="book" className="btn-primary">
              Book a Bus <span>→</span>
            </Link>

            <a href="#fleet" className="btn-secondary">
              Explore Our Fleet
            </a>
          </div>

          <div className="hero-stats">
            <div>
              <strong>Reliable</strong>
              <span>Transport Service</span>
            </div>
            <div>
              <strong>Flexible</strong>
              <span>Travel Solutions</span>
            </div>
            <div>
              <strong>Personal</strong>
              <span>Customer Support</span>
            </div>
          </div>
        </div>
      </div>
    </header>
  );
}

export default CustomerHeader;
