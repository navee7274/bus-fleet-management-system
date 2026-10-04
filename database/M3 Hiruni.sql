USE bus_fleet_management_001;

CREATE TABLE Owner (
    OwnerID INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    Name VARCHAR(100) NOT NULL,
    ContactNo VARCHAR(20)
);
Describe Owner;

INSERT INTO Owner VALUES(1, "admin", 1234, "CCC", "1000202002");

CREATE TABLE ExpenceLog(
ExpenceID INT AUTO_INCREMENT PRIMARY KEY,
Expencedate DATE NOT NULL,
BRegistrationNo VARCHAR(20) NOT NULL,
Category VARCHAR(20) NOT NULL,
PaymentMethod VARCHAR(30) NOT NULL,
Amount DECIMAL(10,2),
ExpenseDescription VARCHAR(50),
CONSTRAINT ET1 CHECK( Amount > 0),
CONSTRAINT ET2 CHECK( Category IN ('Insurance', 'Tires', 'Spare Parts', 'Other Staff Wages', 'Other')),
CONSTRAINT ET3 CHECK( PaymentMethod IN('Cash', 'Bank Transfer', 'Cheque', 'Credit', 'Other')),
CONSTRAINT fk_expense_bus FOREIGN KEY (BRegistrationNo) REFERENCES Bus(BRegistrationNo)
);

CREATE TABLE MaintenanceLog (
MaintenanceID INT AUTO_INCREMENT PRIMARY KEY,
MaintenanceDate DATE NOT NULL,
BRegistrationNo VARCHAR(20) NOT NULL,
MType VARCHAR(30) NOT NULL,
MDescription VARCHAR(255) NOT NULL,
Cost DECIMAL(10,2) NOT NULL,
Odometer DECIMAL(10,2),
NextServiceDue VARCHAR(100),
CONSTRAINT fk_maintenance_bus FOREIGN KEY (BRegistrationNo) REFERENCES Bus(BRegistrationNo),
CONSTRAINT MT1 CHECK( MType IN ('Service' , 'Repair')),
CONSTRAINT MT2 CHECK( Cost > 0 )
);

DESCRIBE Journey;

DROP TABLE Journey;
<<<<<<< HEAD

INSERT INTO Owner (Username, Password, Name, ContactNo) VALUES
('madmin', 'scrypt:32768:8:1$hash_placeholder', 'Main Admin', '0771234567'),
('manager_poorna', 'scrypt:32768:8:1$hash_placeholder', 'Poorna Jayasooriya', '0719876543');

INSERT INTO ExpenceLog (Expensedate, BRegistrationNo, Category, PaymentMethod, Amount, ExpenseDescription) VALUES
('2026-03-03', 'ND-4521', 'Spare Parts', 'Cash', 12500.00, 'Replaced brake pads'),
('2026-03-04', 'WP-ND-8890', 'Insurance', 'Bank Transfer', 185000.00, 'Annual comprehensive insurance'),
('2026-03-08', 'NC-5612', 'Tires', 'Cheque', 96000.00, 'Purchased 2 rear tires'),
('2026-03-12', 'SP-6701', 'Other Staff Wages', 'Cash', 5000.00, 'Cleaner allowance for Yala trip');

INSERT INTO MaintenanceLog (MaintenanceDate, BRegistrationNo, MType, MDescription, Cost, Odometer, NextServiceDue) VALUES
('2026-02-15', 'ND-4521', 'Service', 'Full engine oil change and filter replacement', 35000.00, 119500.00, '124,500 KM'),
('2026-02-20', 'WP-ND-8890', 'Repair', 'Air conditioning compressor repair', 48000.00, 84200.00, 'Immediate checkup on next tour'),
('2026-03-01', 'NC-5612', 'Service', 'Gearbox oil replacement and general check', 28000.00, 144800.00, '150,000 KM');
=======
DROP TABLE ExpenceLog;
>>>>>>> c6c0c034c5d5d0bdb27dfce18b2081965a477627
