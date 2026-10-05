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

CREATE TABLE ExpenseLog(
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

INSERT INTO Owner (Username, Password, Name, ContactNo) VALUES
('madmin', 'scrypt:32768:8:1$hash_placeholder', 'Main Admin', '0771234567'),
('manager_poorna', 'scrypt:32768:8:1$hash_placeholder', 'Poorna Jayasooriya', '0719876543');

INSERT INTO ExpenseLog (Expensedate, BRegistrationNo, Category, PaymentMethod, Amount, ExpenseDescription) VALUES
('2025-10-02', 'ND-4521', 'Tires', 'Cash', 45000.00, 'Replaced front tires'),
('2025-10-04', 'WP-ND-8890', 'Spare Parts', 'Credit', 18500.00, 'Replaced engine belt'),
('2025-10-06', 'NC-5612', 'Other', 'Cash', 7500.00, 'Vehicle washing and cleaning'),
('2025-10-07', 'SP-6701', 'Spare Parts', 'Bank Transfer', 32000.00, 'Replaced suspension parts'),
('2025-10-09', 'ND-4521', 'Other Staff Wages', 'Cash', 6500.00, 'Driver overtime payment'),
('2025-10-10', 'WP-ND-8890', 'Tires', 'Cheque', 88000.00, 'Purchased 2 new tires'),
('2025-10-11', 'NC-5612', 'Spare Parts', 'Cash', 14500.00, 'Replaced oil filter and parts'),
('2025-10-13', 'SP-6701', 'Other', 'Bank Transfer', 12000.00, 'Vehicle service and inspection'),
('2025-10-14', 'ND-4521', 'Insurance', 'Bank Transfer', 175000.00, 'Annual vehicle insurance'),
('2025-10-15', 'WP-ND-8890', 'Other Staff Wages', 'Cash', 5500.00, 'Cleaner allowance'),
('2025-10-17', 'NC-5612', 'Tires', 'Credit', 92000.00, 'Replaced rear tires'),
('2025-10-18', 'SP-6701', 'Spare Parts', 'Cheque', 27500.00, 'Brake system repair'),
('2025-10-20', 'ND-4521', 'Other', 'Cash', 8500.00, 'Interior maintenance'),
('2025-10-21', 'WP-ND-8890', 'Spare Parts', 'Bank Transfer', 22000.00, 'Replaced battery'),
('2025-10-23', 'NC-5612', 'Other Staff Wages', 'Cash', 7000.00, 'Driver overtime payment'),
('2025-10-24', 'SP-6701', 'Tires', 'Cheque', 99000.00, 'Purchased 2 rear tires'),
('2025-10-26', 'ND-4521', 'Spare Parts', 'Credit', 16500.00, 'Replaced headlights'),
('2025-10-27', 'WP-ND-8890', 'Other', 'Cash', 6000.00, 'Vehicle cleaning'),
('2025-10-29', 'NC-5612', 'Spare Parts', 'Bank Transfer', 35500.00, 'Engine maintenance parts'),
('2025-10-31', 'SP-6701', 'Other Staff Wages', 'Cash', 8000.00, 'Staff allowance');

INSERT INTO MaintenanceLog (MaintenanceDate, BRegistrationNo, MType, MDescription, Cost, Odometer, NextServiceDue) VALUES

-- ND-4521
('2025-10-06', 'ND-4521', 'Service', 'Engine oil and filter replacement', 18500.00, 12720.00, '2025-11-06'),
('2025-10-12', 'ND-4521', 'Repair', 'Brake pad replacement', 32500.00, 12845.50, '2026-01-12'),
('2025-10-19', 'ND-4521', 'Service', 'Full vehicle service and inspection', 24000.00, 13010.00, '2026-01-19'),
('2025-10-25', 'ND-4521', 'Repair', 'Air conditioning system repair', 28500.00, 13180.50, '2026-01-25'),
('2025-10-31', 'ND-4521', 'Service', 'Engine oil change and general checkup', 16500.00, 13290.00, '2026-01-31'),

-- WP-ND-8890
('2025-10-05', 'WP-ND-8890', 'Service', 'Engine oil and filter replacement', 19000.00, 18480.00, '2025-11-05'),
('2025-10-11', 'WP-ND-8890', 'Repair', 'Brake system repair', 35000.00, 18590.50, '2026-01-11'),
('2025-10-18', 'WP-ND-8890', 'Service', 'Transmission inspection and service', 27500.00, 18720.00, '2026-01-18'),
('2025-10-23', 'WP-ND-8890', 'Repair', 'Electrical system repair', 22000.00, 18840.75, '2026-01-23'),
('2025-10-30', 'WP-ND-8890', 'Service', 'Full vehicle inspection', 21000.00, 18960.00, '2026-01-30'),

-- NC-5612
('2025-10-05', 'NC-5612', 'Service', 'Engine oil and filter change', 17500.00, 22240.00, '2025-11-05'),
('2025-10-12', 'NC-5612', 'Repair', 'Suspension repair', 42000.00, 22310.50, '2026-01-12'),
('2025-10-20', 'NC-5612', 'Service', 'Brake inspection and servicing', 23000.00, 22490.00, '2026-01-20'),
('2025-10-25', 'NC-5612', 'Repair', 'Radiator repair', 31500.00, 22620.25, '2026-01-25'),
('2025-10-31', 'NC-5612', 'Service', 'General maintenance service', 19500.00, 22750.00, '2026-01-31'),

-- SP-6701
('2025-10-02', 'SP-6701', 'Service', 'Engine oil and filter replacement', 18000.00, 9960.00, '2025-11-02'),
('2025-10-09', 'SP-6701', 'Repair', 'Brake pad and disc repair', 38000.00, 10040.50, '2026-01-09'),
('2025-10-15', 'SP-6701', 'Service', 'Transmission service', 29000.00, 10185.00, '2026-01-15'),
('2025-10-22', 'SP-6701', 'Repair', 'Battery and electrical repair', 24000.00, 10290.75, '2026-01-22'),
('2025-10-29', 'SP-6701', 'Service', 'Full vehicle service', 26000.00, 10390.00, '2026-01-29');

DROP TABLE ExpenseLog;
Describe ExpenseLog;

/* Monthly Maintenance Expenses */
SELECT
    COUNT(MaintenanceID) AS TotalMaintenanceRecords,
    COALESCE(SUM(Cost), 0) AS TotalMaintenanceCost
FROM MaintenanceLog
WHERE MaintenanceDate >= '2025-10-01' AND MaintenanceDate < '2025-11-01';

/* Monthly Other Expenses */
SELECT
    COUNT(ExpenseID) AS TotalExpenseRecords,
    COALESCE(SUM(Amount), 0) AS TotalOtherExpenses
FROM ExpenseLog
WHERE Expensedate >= '2025-10-01' AND Expensedate < '2025-11-01';

/* Expenses by Category */
SELECT
    Category,
    COUNT(ExpenseID) AS NumberOfExpenses,
    COALESCE(SUM(Amount), 0) AS TotalAmount
FROM ExpenseLog
WHERE Expensedate >= '2025-10-01' AND Expensedate < '2025-11-01'
GROUP BY Category
ORDER BY TotalAmount DESC;

/* Maintenance Cost by Bus */
SELECT
    BRegistrationNo,
    COUNT(MaintenanceID) AS MaintenanceCount,
    COALESCE(SUM(Cost), 0) AS TotalMaintenanceCost
FROM MaintenanceLog
WHERE MaintenanceDate >= '2025-10-01' AND MaintenanceDate < '2025-11-01'
GROUP BY BRegistrationNo
ORDER BY TotalMaintenanceCost DESC;

