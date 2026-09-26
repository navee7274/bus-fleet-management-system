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

/* The Final Bus Table 
BRegistrationNo | BPurchaseDate | BPurchasePrice | BCapacity | BNotes | BActive */

/* The Final Driver Table 
DriverID | Name | NIC | ContactNo | BaseMonthlySalary | PerTripAllowence | notes | DActive*/
