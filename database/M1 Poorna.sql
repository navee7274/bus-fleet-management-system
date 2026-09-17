USE bus_fleet_management_001;

-- Creating the Bus Table
CREATE TABLE Bus(
BRegistrationNo VARCHAR(20) PRIMARY KEY,
BPurchaseDate DATE NOT NULL,
BPurchasePrice DECIMAL(10,2) NOT NULL,
BNotes VARCHAR(225),
BActive BOOLEAN DEFAULT TRUE NOT NULL
);

-- Adding the Capacity column to the Bus table
ALTER TABLE Bus ADD COLUMN BCapacity INT AFTER BPurchasePrice;

SELECT *FROM Bus;

/* The Final Bus Table 
BRegistrationNo | BPurchaseDate | BPurchasePrice | BCapacity | BNotes | BActive */

-- Creating the FuelLog Table
CREATE TABLE FuelLog(
FDate DATE NOT NULL,
FPrice DECIMAL(10,2) NOT NULL,
FCost DECIMAL(10,2) NOT NULL,
FLitersfilled DECIMAL(10,2) GENERATED ALWAYS AS(FCost/FPrice) STORED
);

SELECT *FROM Bus;

/* The Final Table
FDate | FFuelPrice | FCost | FLitersFilled */


