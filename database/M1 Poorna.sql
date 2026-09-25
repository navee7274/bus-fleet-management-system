USE bus_fleet_management_001;

-- Journey Table
CREATE TABLE Journey(
JourneyID INT AUTO_INCREMENT PRIMARY KEY,
JourneyDate DATE NOT NULL,
BRegistrationNo VARCHAR(20) NOT NULL,
DriverID VARCHAR(30) NOT NULL,
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
CHECK (IncomeAmount) >= 0)
);

/* The Final Table
JourneyID | JourneyDate | BRegistrationNo | DriverID | Purpose | ClientDestination | StartOdometer | EndOdometer | KMTraveled | IncomeAmount | Notes
*/


-- Creating the FuelLog Table
CREATE TABLE FuelLog(
FDate DATE NOT NULL,
FPrice DECIMAL(10,2) NOT NULL,
FCost DECIMAL(10,2) NOT NULL,
FLitersfilled DECIMAL(10,2) GENERATED ALWAYS AS(FCost/FPrice) STORED
);

SELECT *FROM Bus;
DROP TABLE Bus;

DESCRIBE bus;

/* The Final Table
FDate | FFuelPrice | FCost | FLitersFilled */


