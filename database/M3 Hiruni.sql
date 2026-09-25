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
ExpenseID INT AUTO_INCREMENT PRIMARY KEY,
Expensedate DATE NOT NULL,
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