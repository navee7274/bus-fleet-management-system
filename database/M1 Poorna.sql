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
('2025-10-02', 'ND-4521', 'DRV001', 'School Trip', 'Kandy', 12500.00, 12685.50, 25000.00, 'School excursion'),
('2025-10-02', 'WP-ND-8890', 'DRV002', 'Airport Transfer', 'Bandaranaike Airport',18320.00, 18410.75, 15000.00, 'Airport pickup'),
('2025-10-03', 'NC-5612', 'DRV003', 'Staff Transport', 'Colombo', 22100.00, 22165.25, 8000.00, 'Company staff transport'),
('2025-10-04', 'SP-6701', 'DRV001', 'Tour', 'Nuwara Eliya', 12685.50, 12890.00, 30000.00, 'Weekend tour'),
('2025-10-05', 'ND-4521', 'DRV004', 'School Trip', 'Galle', 9800.00, 9915.50, 18000.00, 'School educational trip'),
('2025-10-07', 'WP-ND-8890', 'DRV002', 'Airport Transfer', 'Colombo', 18410.75, 18485.00, 12000.00, 'Airport drop-off'),
('2025-10-08', 'SP-6701', 'DRV003', 'Tour', 'Bentota', 22165.25, 22280.75, 22000.00, 'Tour group transport'),
('2025-10-10', 'ND-4521', 'DRV001', 'Staff Transport', 'Negombo', 15750.00, 15835.50, 9000.00, 'Staff meeting'),
('2025-10-11', 'NC-5612', 'DRV001', 'Wedding', 'Kurunegala', 12890.00, 13045.25, 28000.00, 'Wedding transport'),
('2025-10-12', 'SP-6701', 'DRV004', 'School Trip', 'Matara', 9915.50, 10080.00, 20000.00, 'School trip'),
('2025-10-14', 'WP-ND-8890', 'DRV002', 'Tour', 'Sigiriya', 18485.00, 18620.50, 35000.00, 'Tourist group'),
('2025-10-15', 'ND-4521', 'DRV003', 'Airport Transfer', 'Bandaranaike Airport', 22280.75, 22350.25, 14000.00, 'Airport transfer'),
('2025-10-17', 'SP-6701', 'DRV002', 'School Trip', 'Kandy', 15835.50, 16020.00, 26000.00, 'School excursion'),
('2025-10-18', 'NC-5612', 'DRV001', 'Staff Transport', 'Gampaha', 13045.25, 13120.75, 7500.00, 'Staff transport'),
('2025-10-20', 'SP-6701', 'DRV004', 'Tour', 'Ella', 10080.00, 10275.50, 40000.00, 'Tour group'),
('2025-10-22', 'ND-4521', 'DRV002', 'Wedding', 'Kegalle', 18620.50, 18755.25, 24000.00, 'Wedding transport'),
('2025-10-24', 'SP-6701', 'DRV003', 'School Trip', 'Ratnapura', 22350.25, 22465.00, 19000.00, 'School educational trip'),
('2025-10-26', 'WP-ND-8890', 'DRV002', 'Airport Transfer', 'Bandaranaike Airport', 16020.00, 16105.50, 13000.00, 'Airport pickup'),
('2025-10-28', 'ND-4521', 'DRV001', 'Tour', 'Dambulla', 13120.75, 13265.25, 32000.00, 'Tourist transport'),
('2025-10-30', 'NC-5612', 'DRV004', 'Staff Transport', 'Colombo', 10275.50, 10340.00, 8500.00, 'Company staff transport');

INSERT INTO FuelLog (BRegistrationNo, FDate, FPrice, FCost) 
VALUES
('ND-4521', '2025-10-01', 370.00, 29600.00),
('WP-ND-8890', '2025-10-02', 370.00, 25900.00),
('NC-5612', '2025-10-05', 370.00, 37000.00),
('SP-6701', '2025-10-10', 370.00, 44400.00),
('WP-ND-8890', '2025-10-11', 370.00, 29600.00),
('NC-5612', '2025-10-12', 370.00, 35150.00),
('SP-6701', '2025-10-13', 370.00, 38850.00),
('ND-4521', '2025-10-15', 370.00, 37000.00),
('WP-ND-8890', '2025-10-16', 370.00, 31450.00),
('NC-5612', '2025-10-18', 370.00, 33300.00),
('SP-6701', '2025-10-19', 370.00, 42550.00),
('ND-4521', '2025-10-21', 370.00, 29600.00),
('WP-ND-8890', '2025-10-22', 370.00, 27750.00),
('NC-5612', '2025-10-24', 370.00, 37000.00),
('SP-6701', '2025-10-25', 370.00, 44400.00),
('ND-4521', '2025-10-27', 370.00, 35150.00),
('WP-ND-8890', '2025-10-28', 370.00, 33300.00),
('NC-5612', '2025-10-30', 370.00, 31450.00),
('SP-6701', '2025-10-31', 370.00, 40700.00);

/* The Final Table
FuelLogID | BRegistrationNo | FDate | FFuelPrice | FCost | FLitersFilled */

SELECT StartOdometer, EndOdometer, KMTRavelled FROM Journey;
SELECT FCost, FPrice, FLitersfilled FROM FuelLog;

/* Monthly Journey Summary*/
SELECT
    COUNT(JourneyID) AS TotalJourneys,
    COALESCE(SUM(KMTravelled), 0) AS TotalKMTravelled,
    COALESCE(SUM(IncomeAmount), 0) AS TotalJourneyIncome
FROM Journey
WHERE JourneyDate >= '2025-10-01' AND JourneyDate < '2025-11-01';

/* Monthly Journey Report by Bus */
SELECT
    BRegistrationNo,
    COUNT(JourneyID) AS TotalJourneys,
    COALESCE(SUM(KMTravelled), 0) AS TotalKM,
    COALESCE(SUM(IncomeAmount), 0) AS TotalIncome
FROM Journey
WHERE JourneyDate >= '2025-10-01' AND JourneyDate < '2025-11-01'
GROUP BY BRegistrationNo
ORDER BY TotalIncome DESC;

/* Monthly Fuel Report */
SELECT
    COUNT(FuelLogID) AS FuelRecords,
    COALESCE(SUM(FCost), 0) AS TotalFuelCost,
    COALESCE(SUM(FLitersfilled), 0) AS TotalFuelLitres
FROM FuelLog
WHERE FDate >= '2025-10-01' AND FDate < '2025-11-01';

/* Fuel Cost by Bus */
SELECT
    BRegistrationNo,
    COUNT(FuelLogID) AS FuelRecords,
    COALESCE(SUM(FLitersfilled), 0) AS TotalLitres,
    COALESCE(SUM(FCost), 0) AS TotalFuelCost
FROM FuelLog
WHERE FDate >= '2025-10-01' AND FDate < '2025-11-01'
GROUP BY BRegistrationNo
ORDER BY TotalFuelCost DESC;