USE bus_fleet_management_001;

CREATE TABLE Bus(
BRegistrationNo VARCHAR(20) PRIMARY KEY,
BPurchaseDate DATE,
BPurchasePrice DECIMAL(10,2),
BNotes VARCHAR(225),
BActive BOOLEAN DEFAULT TRUE
);


