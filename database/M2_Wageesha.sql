USE bus_fleet_management_001;

-- Driver table.

CREATE TABLE Driver (
    DriverID CHAR(10) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    NIC VARCHAR(20),
    ContactNo VARCHAR(20),
    BaseMonthlySalary DECIMAL(10,2) NOT NULL,
    PerTripAllowence DECIMAL(10,2) NOT NULL,
    notes VARCHAR(500)
);
ALTER TABLE Driver ADD COLUMN  DActive BOOLEAN AFTER notes;

SELECT * FROM Driver;

/*sample queries
Driver table*/
INSERT INTO Driver (DriverID, Name, NIC, ContactNo, BaseMonthlySalary, PerTripAllowence, notes, DActive) VALUES
('DRV001', 'Kamal Perera', '198512345678', '0773344556', 65000.00, 2500.00, 'Senior driver, clean record', TRUE),
('DRV002', 'Sunil Fernando', '198823456789', '0714455667', 60000.00, 2000.00, 'Experienced in hill routes', TRUE),
('DRV003', 'Nimal Jayasinghe', '199234567890', '0755566778', 58000.00, 2000.00, 'Night shift preferred', TRUE),
('DRV004', 'Saman Kumara', '199045678901', '0786677889', 62000.00, 2200.00, 'On leave occasionally', TRUE);

-- BUS table.
CREATE TABLE Bus(
    BRegistrationNo VARCHAR(20) PRIMARY KEY,
    BPurchaseDate DATE NOT NULL,
    BPurchasePrice DECIMAL(10,2) NOT NULL,
    BNotes VARCHAR(225),
    BActive BOOLEAN DEFAULT TRUE NOT NULL,
    BCapacity INT
);
SELECT *FROM Bus;

/*sample queries
Bus table*/
INSERT INTO Bus (BRegistrationNo, BPurchaseDate, BPurchasePrice, BCapacity, BNotes, BActive) VALUES
('ND-4521', '2020-01-15', 8500000.00, 54, 'Leyland Viking - Main Fleet', TRUE),
('WP-ND-8890', '2021-06-10', 9200000.00, 49, 'AC Luxury Coach', TRUE),
('NC-5612', '2019-11-01', 7800000.00, 54, 'Leyland Viking - Long Distance', TRUE),
('SP-6701', '2022-03-20', 10500000.00, 42, 'Super Luxury Tourist Bus', TRUE);

TRUNCATE TABLE Driver;
TRUNCATE TABLE Bus;
SELECT * FROM BUS;
/* The Final Bus Table 
BRegistrationNo | BPurchaseDate | BPurchasePrice | BCapacity | BNotes | BActive */

/* The Final Driver Table 
DriverID | Name | NIC | ContactNo | BaseMonthlySalary | PerTripAllowence | notes | DActive*/

/* Monthly Driver salary */
SELECT
    d.DriverID,
    d.Name,
    d.BaseMonthlySalary,
    COUNT(j.JourneyID) AS TotalTrips,
    d.PerTripAllowence,
    COUNT(j.JourneyID) * d.PerTripAllowence AS TotalTripAllowance,
    d.BaseMonthlySalary +
        (COUNT(j.JourneyID) * d.PerTripAllowence) AS TotalSalary
FROM Driver d
LEFT JOIN Journey j
    ON d.DriverID = j.DriverID
    AND j.JourneyDate >= '2025-10-01'
    AND j.JourneyDate < '2025-11-01'
WHERE d.DActive = TRUE
GROUP BY
    d.DriverID,
    d.Name,
    d.BaseMonthlySalary,
    d.PerTripAllowence
ORDER BY d.Name;

/* individual Driver Salary */
SELECT
    d.DriverID,
    d.Name,
    d.BaseMonthlySalary,
    COUNT(j.JourneyID) AS TotalTrips,
    d.PerTripAllowence,
    COUNT(j.JourneyID) * d.PerTripAllowence AS TotalTripAllowance,
    d.BaseMonthlySalary +
        (COUNT(j.JourneyID) * d.PerTripAllowence) AS TotalSalary
FROM Driver d
LEFT JOIN Journey j
    ON d.DriverID = j.DriverID
    AND j.JourneyDate >= '2025-10-01'
    AND j.JourneyDate < '2025-11-01'
WHERE d.DriverID = 'DRV001'
  AND d.DActive = TRUE
GROUP BY
    d.DriverID,
    d.Name,
    d.BaseMonthlySalary,
    d.PerTripAllowence;
    
/* Totsl Driver Salary*/
SELECT
    COALESCE(SUM(
        d.BaseMonthlySalary +
        (
            SELECT COUNT(*)
            FROM Journey j
            WHERE j.DriverID = d.DriverID
              AND j.JourneyDate >= '2025-10-01'
              AND j.JourneyDate < '2025-11-01'
        ) * d.PerTripAllowence
    ), 0) AS TotalDriverSalary
FROM Driver d
WHERE d.DActive = TRUE;

/* Driver Performance*/
SELECT
    d.DriverID,
    d.Name,
    COUNT(j.JourneyID) AS TotalJourneys,
    COALESCE(SUM(j.KMTravelled), 0) AS TotalKM,
    COALESCE(SUM(j.IncomeAmount), 0) AS TotalIncome,
    d.BaseMonthlySalary,
    d.PerTripAllowence,
    d.BaseMonthlySalary +
        (COUNT(j.JourneyID) * d.PerTripAllowence) AS TotalSalary
FROM Driver d
LEFT JOIN Journey j
    ON d.DriverID = j.DriverID
    AND j.JourneyDate >= '2025-10-01'
    AND j.JourneyDate < '2025-11-01'
WHERE d.DActive = TRUE
GROUP BY
    d.DriverID,
    d.Name,
    d.BaseMonthlySalary,
    d.PerTripAllowence
ORDER BY TotalJourneys DESC;


