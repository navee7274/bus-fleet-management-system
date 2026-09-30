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
INSERT INTO Driver
(DriverID, Name, NIC, ContactNo, BaseMonthlySalary, PerTripAllowence, notes, DActive)
VALUES
('D001', 'Kamal Perera', '199012345678', '0771234567', 75000.00, 2500.00, 'Experienced driver', TRUE),
('D002', 'Nimal Silva', '198912345679', '0712345678', 80000.00, 3000.00, 'Senior driver', TRUE),
('D003', 'Sunil Fernando', '199112345680', '0763456789', 70000.00, 2000.00, 'New driver', TRUE);

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
INSERT INTO Bus
(BRegistrationNo, BPurchaseDate, BPurchasePrice, BCapacity, BNotes, BActive)
VALUES
('NB-1234', '2024-05-10', 12500000.00, 45, 'School bus', TRUE),
('NB-5678', '2023-08-15', 15000000.00, 50, 'Tour bus', TRUE),
('WP-9012', '2025-01-20', 18000000.00, 55, 'New bus', TRUE);

/* The Final Bus Table 
BRegistrationNo | BPurchaseDate | BPurchasePrice | BCapacity | BNotes | BActive */

/* The Final Driver Table 
DriverID | Name | NIC | ContactNo | BaseMonthlySalary | PerTripAllowence | notes | DActive*/


