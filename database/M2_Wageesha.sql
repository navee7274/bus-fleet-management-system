USE bus_fleet_management_001;

-- Driver table

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

CREATE TABLE Driver_Salary (
    CurrentYear int NOT NULL,
    CurrentMonth int NOT NULL,
    TripsCompleted int,
    BaseSalary DECIMAL(10, 2),
    PerTripAllowance DECIMAL(10, 2),
    MonthlySalary DECIMAL(10,2),
	CONSTRAINT fkDriverID FOREIGN KEY (DriverID) REFERENCES Driver(DriverID ) ON DELETE CASCADE ON UPDATE CASCADE
);
SELECT * FROM Driver_Salary;

DROP TABLE Driver_Salary;

-- BUS table
CREATE TABLE Bus(
    BRegistrationNo VARCHAR(20) PRIMARY KEY,
    BPurchaseDate DATE NOT NULL,
    BPurchasePrice DECIMAL(10,2) NOT NULL,
    BNotes VARCHAR(225),
    BActive BOOLEAN DEFAULT TRUE NOT NULL,
    BCapacity INT
);
SELECT *FROM Bus;
