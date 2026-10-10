import Navbar from "../components/CustomerNavBar";
import Header from "../components/CustomerHeader";
import Footer from "../components/CustomerFooter";
import "../styles/LandingPage.css";

import { Link } from "react-router-dom";

function LandingPage() {
  return (
    <div className="landing-page">
      <Navbar />
      <Header />

      <main>
        <section className="services section" id="services">
          <div className="section-heading">
            <span className="section-tag">WHAT WE OFFER</span>
            <h2>Travel made simple.</h2>
            <p>The right transport for every occasion.</p>
          </div>

          <div className="service-grid">
            <article className="service-card">
              <span className="service-icon">↗</span>
              <h3>Tour Packages</h3>
              <p>Explore destinations across Sri Lanka with convenient group transport.</p>
            </article>

            <article className="service-card">
              <span className="service-icon">▣</span>
              <h3>Corporate Travel</h3>
              <p>Organise transport for company events, conferences, and staff outings.</p>
            </article>

            <article className="service-card">
              <span className="service-icon">✦</span>
              <h3>Private Hire</h3>
              <p>Plan transport for weddings, family trips, school events, and special occasions.</p>
            </article>
          </div>
        </section>

        <section className="fleet-section section" id="fleet">
          <div className="fleet-content">
            <span className="section-tag">OUR FLEET</span>
            <h2>The right bus for your journey.</h2>
            <p>Whether you're planning a small group outing or a large trip, we'll help you find the right transport for your needs.</p>
            <Link to="book" className="btn-primary">
              Book a Bus →
            </Link>
          </div>
        </section>
      </main>

      <Footer />
    </div>
  );
}

export default LandingPage;
