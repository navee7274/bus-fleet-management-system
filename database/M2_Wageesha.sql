USE bus_fleet_management_001;

CREATE TABLE Driver (
    DriverID CHAR(10) PRIMARY KEY,
    DriverNo VARCHAR(100) NOT NULL,
    DriverNIC VARCHAR(20),
    DContactNo VARCHAR(20),
    BaseMonthlySalary DECIMAL(10,2) NOT NULL,
    PerTripAllowence DECIMAL(10,2) NOT NULL,
    notes VARCHAR(500)
);
SELECT * FROM Driver;
