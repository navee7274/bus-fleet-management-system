USE bus_fleet_management_001;

-- Journey Table
CREATE TABLE Journey(
JourneyID INT AUTO_INCREMENT PRIMARY KEY,
JourneyDate DATE NOT NULL,
BRegistrationNo VARCHAR(20) NOT NULL,
DriverID CHAR(10) NOT NULL,
Purpose VARCHAR(30) NOT NULL,
ClientDestination VARCHAR(225) NOT NULL,
StartOdometer DECIMAL(10,2) NOT NULL,
EndOdometer DECIMAL(10,2) NOT NULL,
KMTravelled DECIMAL(10,2) GENERATED ALWAYS AS (EndOdometer - StartOdometer) STORED,
IncomeAmount DECIMAL(10,2) DEFAULT 0.00,
Notes VARCHAR(255),
FOREIGN KEY (BRegistrationNo) REFERENCES Bus(BRegistrationNo),
FOREIGN KEY (DriverID) REFERENCES Driver(DriverID),
CHECK (EndOdometer >= StartOdometer),
CHECK (IncomeAmount >= 0)
);

/* The Final Table
JourneyID | JourneyDate | BRegistrationNo | DriverID | Purpose | ClientDestination | StartOdometer | EndOdometer | KMTraveled | IncomeAmount | Notes
*/


-- Creating the FuelLog Table
CREATE TABLE FuelLog(
FuelLogID INT AUTO_INCREMENT PRIMARY KEY,
BRegistrationNo VARCHAR(20) NOT NULL,
FDate DATE NOT NULL,
FPrice DECIMAL(10,2) NOT NULL,
FCost DECIMAL(10,2) NOT NULL,
FLitersfilled DECIMAL(10,2) GENERATED ALWAYS AS(FCost/FPrice) STORED,
FOREIGN KEY (BRegistrationNo) REFERENCES Bus(BRegistrationNo)
);

DROP TABLE FuelLog;
DROP TABLE Journey;

DESCRIBE FuelLog;
DESCRIBE Journey;

INSERT INTO Journey (JourneyDate, BRegistrationNo, DriverID, Purpose, ClientDestination, StartOdometer, EndOdometer, IncomeAmount, Notes) 
VALUES
('2026-03-01', 'ND-4521', 'DRV001', 'Hire', 'Kandy', 120000.00, 120280.00, 45000.00, 'School trip hire'),
('2026-03-02', 'WP-ND-8890', 'DRV002', 'Hire', 'Galle', 85000.00, 85240.00, 52000.00, 'Corporate tour'),
('2026-03-05', 'NC-5612', 'DRV003', 'Route', 'Anuradhapura', 145000.00, 145410.00, 38000.00, 'Regular daily route'),
('2026-03-10', 'SP-6701', 'DRV004', 'Hire', 'Yala', 42000.00, 42620.00, 95000.00, '3-day safari charter');

INSERT INTO FuelLog (BRegistrationNo, FDate, FPrice, FCost) 
VALUES
('ND-4521', '2026-03-01', 370.00, 29600.00),
('WP-ND-8890', '2026-03-02', 370.00, 25900.00),
('NC-5612', '2026-03-05', 370.00, 37000.00),
('SP-6701', '2026-03-10', 370.00, 44400.00);

/* The Final Table
FuelLogID | BRegistrationNo | FDate | FFuelPrice | FCost | FLitersFilled */


