USE bus_fleet_management_001;

CREATE TABLE Bus(
BRegistrationNo VARCHAR(20) PRIMARY KEY,
BPurchaseDate DATE,
BPurchasePrice DECIMAL(10,2),
BNotes VARCHAR(225),
BActive BOOLEAN DEFAULT TRUE
);

-- Adding the Capacity column to the Bus table
ALTER TABLE Bus ADD COLUMN BCapacity INT AFTER BPurchasePrice;

SELECT *FROM Bus;

/* The Final Table 
BRegistrationNo | BPurchaseDate | BPurchasePrice | BCapacity | BNotes | BActive */
