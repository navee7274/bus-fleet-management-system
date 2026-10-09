USE bus_fleet_management_001;
show tables;

DROP TABLE Payment;
DROP TABLE Booking;

-- Booking Table

CREATE TABLE Booking (
    BookingID INT AUTO_INCREMENT PRIMARY KEY,

    CustomerFirstName VARCHAR(20) NOT NULL,
    CustomerLastName VARCHAR(20) NOT NULL,
    CustomerContactPhone VARCHAR(10) NOT NULL,
    CustomerContactEmail VARCHAR(70) NOT NULL,
    CustomerAddress VARCHAR(100) NOT NULL,
    CustomerCity VARCHAR(50) NOT NULL,

    StartDateTime DATETIME NOT NULL,
    EndDateTime DATETIME NOT NULL,

    StartLocation VARCHAR(255) NOT NULL,
    Destination VARCHAR(255) NOT NULL,

    PassengerCount INT NOT NULL,

    BRegistrationNo VARCHAR(20),
    DriverID CHAR(10),

    EstimatedCost DECIMAL(12,2) NOT NULL,
    FinalPrice DECIMAL(12,2),

    AdvanceAmount DECIMAL(12,2) NOT NULL DEFAULT 8000.00,

    BookingStatus ENUM(
        'PENDING',
        'PAYMENT_PENDING',
        'CONFIRMED',
        'COMPLETED',
        'REJECTED',
        'CANCELLED'
    ) NOT NULL DEFAULT 'PENDING',

    CreatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    JourneyID INT,

    CONSTRAINT fk_booking_bus
        FOREIGN KEY (BRegistrationNo)
        REFERENCES Bus(BRegistrationNo),

    CONSTRAINT fk_booking_driver
        FOREIGN KEY (DriverID)
        REFERENCES Driver(DriverID),

    CONSTRAINT fk_booking_journey
        FOREIGN KEY (JourneyID)
        REFERENCES Journey(JourneyID),

    CONSTRAINT chk_booking_passengers
        CHECK (PassengerCount > 0),

    CONSTRAINT chk_booking_datetime
        CHECK (EndDateTime > StartDateTime),

    CONSTRAINT chk_booking_estimated_cost
        CHECK (EstimatedCost >= 0),

    CONSTRAINT chk_booking_final_price
        CHECK (FinalPrice IS NULL OR FinalPrice >= 0),

    CONSTRAINT chk_booking_advance_amount
        CHECK (AdvanceAmount >= 0)
);

DESCRIBE Booking;


CREATE TABLE Payment (
    PaymentID INT AUTO_INCREMENT PRIMARY KEY,
    BookingID INT NOT NULL,
    Amount DECIMAL(12,2) NOT NULL,
	PaymentDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PaymentMethod VARCHAR(50),
    PaymentStatus ENUM(
        'PENDING',
		'PROCESSING',
		'SUCCESS',
		'FAILED',
		'REFUNDED'
    ) NOT NULL DEFAULT 'PENDING',
    TransactionReference VARCHAR(100) UNIQUE,

    CONSTRAINT fk_payment_booking
        FOREIGN KEY (BookingID)
        REFERENCES Booking(BookingID),
    CONSTRAINT chk_payment_amount
        CHECK (Amount > 0)
);

INSERT INTO Booking
(
    CustomerFirstName,
    CustomerLastName,
    CustomerContactPhone,
    CustomerContactEmail,
    CustomerAddress,
    CustomerCity,
    StartDateTime,
    EndDateTime,
    StartLocation,
    Destination,
    PassengerCount,
    BRegistrationNo,
    DriverID,
    EstimatedCost,
    FinalPrice,
    AdvanceAmount,
    BookingStatus,
    JourneyID
)
VALUES

('Kasun', 'Perera', '0712345678', 'kasun.perera@gmail.com',
 '25 Main Street', 'Colombo',
 '2025-10-02 06:30:00', '2025-10-02 18:30:00',
 'Colombo', 'Kandy', 35, 'ND-4521', 'DRV001',
 28000.00, 25000.00, 8000.00, 'COMPLETED', 1),

('Nimali', 'Fernando', '0723456789', 'nimali.fernando@gmail.com',
 '18 Lake Road', 'Negombo',
 '2025-10-02 08:00:00', '2025-10-02 15:00:00',
 'Negombo', 'Bandaranaike Airport', 20, 'WP-ND-8890', 'DRV002',
 17000.00, 15000.00, 8000.00, 'COMPLETED', 2),

('Tharindu', 'Silva', '0774567890', 'tharindu.silva@gmail.com',
 '42 Temple Road', 'Colombo',
 '2025-10-03 07:00:00', '2025-10-03 16:00:00',
 'Colombo', 'Colombo', 30, 'NC-5612', 'DRV003',
 9500.00, 9000.00, 8000.00, 'COMPLETED', 3),

('Sachini', 'Perera', '0765678901', 'sachini.perera@gmail.com',
 '15 Station Road', 'Colombo',
 '2025-10-04 05:30:00', '2025-10-04 20:00:00',
 'Colombo', 'Nuwara Eliya', 40, 'SP-6701', 'DRV001',
 34000.00, 30000.00, 8000.00, 'COMPLETED', 4),

('Ravindu', 'Kumara', '0756789012', 'ravindu.kumara@gmail.com',
 '72 Beach Road', 'Galle',
 '2025-10-05 06:00:00', '2025-10-05 17:00:00',
 'Colombo', 'Galle', 32, 'ND-4521', 'DRV004',
 21000.00, 18000.00, 8000.00, 'COMPLETED', 5),

('Dilshan', 'Jayawardena', '0747890123', 'dilshan.j@gmail.com',
 '10 Park Avenue', 'Colombo',
 '2025-10-07 08:00:00', '2025-10-07 15:00:00',
 'Colombo', 'Bandaranaike Airport', 18, 'WP-ND-8890', 'DRV002',
 14000.00, 12000.00, 8000.00, 'COMPLETED', 6),

('Shalini', 'De Silva', '0788901234', 'shalini.desilva@gmail.com',
 '33 River Road', 'Colombo',
 '2025-10-08 07:00:00', '2025-10-08 18:00:00',
 'Colombo', 'Bentota', 25, 'SP-6701', 'DRV003',
 25000.00, 22000.00, 8000.00, 'COMPLETED', 7),

('Nuwan', 'Bandara', '0719012345', 'nuwan.bandara@gmail.com',
 '55 Hill Street', 'Negombo',
 '2025-10-10 07:30:00', '2025-10-10 16:30:00',
 'Negombo', 'Negombo', 28, 'ND-4521', 'DRV001',
 10000.00, 9000.00, 8000.00, 'COMPLETED', 8),

('Amaya', 'Rathnayake', '0720123456', 'amaya.r@gmail.com',
 '12 Flower Road', 'Colombo',
 '2025-10-11 05:00:00', '2025-10-11 21:00:00',
 'Colombo', 'Kurunegala', 38, 'NC-5612', 'DRV001',
 30000.00, 28000.00, 8000.00, 'COMPLETED', 9),

('Hasitha', 'Wijesinghe', '0771234567', 'hasitha.w@gmail.com',
 '88 Main Street', 'Matara',
 '2025-10-12 06:00:00', '2025-10-12 18:00:00',
 'Colombo', 'Matara', 30, 'SP-6701', 'DRV004',
 23000.00, 20000.00, 8000.00, 'COMPLETED', 10),

('Ishara', 'Gunasekara', '0762345678', 'ishara.g@gmail.com',
 '24 Lake View', 'Colombo',
 '2025-10-14 05:30:00', '2025-10-14 20:00:00',
 'Colombo', 'Sigiriya', 35, 'WP-ND-8890', 'DRV002',
 39000.00, 35000.00, 8000.00, 'COMPLETED', 11),

('Chamod', 'Senanayake', '0753456789', 'chamod.s@gmail.com',
 '19 Temple Road', 'Colombo',
 '2025-10-15 07:00:00', '2025-10-15 16:00:00',
 'Colombo', 'Bandaranaike Airport', 20, 'ND-4521', 'DRV003',
 16000.00, 14000.00, 8000.00, 'COMPLETED', 12),

('Piumi', 'Karunaratne', '0744567890', 'piumi.k@gmail.com',
 '65 School Road', 'Kandy',
 '2025-10-17 06:00:00', '2025-10-17 19:00:00',
 'Colombo', 'Kandy', 40, 'SP-6701', 'DRV002',
 29000.00, 26000.00, 8000.00, 'COMPLETED', 13),

('Rukshan', 'Madushanka', '0785678901', 'rukshan.m@gmail.com',
 '41 Garden Road', 'Gampaha',
 '2025-10-18 07:30:00', '2025-10-18 15:30:00',
 'Gampaha', 'Gampaha', 25, 'NC-5612', 'DRV001',
 8500.00, 7500.00, 8000.00, 'COMPLETED', 14),

('Anjali', 'Wijeratne', '0716789012', 'anjali.w@gmail.com',
 '30 Station Road', 'Colombo',
 '2025-10-20 05:00:00', '2025-10-20 21:00:00',
 'Colombo', 'Ella', 40, 'SP-6701', 'DRV004',
 45000.00, 40000.00, 8000.00, 'COMPLETED', 15),

('Dinesh', 'Perera', '0727890123', 'dinesh.p@gmail.com',
 '14 Main Street', 'Kegalle',
 '2025-10-22 06:00:00', '2025-10-22 19:00:00',
 'Colombo', 'Kegalle', 30, 'ND-4521', 'DRV002',
 27000.00, 24000.00, 8000.00, 'COMPLETED', 16),

('Harini', 'Fernando', '0778901234', 'harini.f@gmail.com',
 '75 Beach Road', 'Ratnapura',
 '2025-10-24 06:30:00', '2025-10-24 18:00:00',
 'Colombo', 'Ratnapura', 28, 'SP-6701', 'DRV003',
 22000.00, 19000.00, 8000.00, 'COMPLETED', 17),

('Kavindu', 'Silva', '0769012345', 'kavindu.s@gmail.com',
 '21 Airport Road', 'Negombo',
 '2025-10-26 07:00:00', '2025-10-26 15:00:00',
 'Negombo', 'Bandaranaike Airport', 20, 'WP-ND-8890', 'DRV002',
 15000.00, 13000.00, 8000.00, 'COMPLETED', 18),

('Sewmi', 'Ranatunga', '0750123456', 'sewmi.r@gmail.com',
 '45 Lake Road', 'Colombo',
 '2025-10-28 06:00:00', '2025-10-28 20:00:00',
 'Colombo', 'Dambulla', 35, 'ND-4521', 'DRV001',
 36000.00, 32000.00, 8000.00, 'COMPLETED', 19),

('Malith', 'Ekanayake', '0741234567', 'malith.e@gmail.com',
 '67 Temple Road', 'Colombo',
 '2025-10-30 07:00:00', '2025-10-30 16:00:00',
 'Colombo', 'Colombo', 25, 'NC-5612', 'DRV004',
 9500.00, 8500.00, 8000.00, 'COMPLETED', 20),
 
 ('Madhavi', 'Silva', '0725678901', 'madhavi.silva@gmail.com',
 '45 Temple Road', 'Gampaha',
 '2025-10-13 06:30:00', '2025-10-13 20:00:00',
 'Gampaha', 'Nuwara Eliya',
 35, NULL, NULL,
 35000.00, NULL, 8000.00,
 'CANCELLED', NULL),

('Rashmi', 'Fernando', '0776789012', 'rashmi.fernando@gmail.com',
 '78 Beach Road', 'Negombo',
 '2025-10-21 08:00:00', '2025-10-21 16:00:00',
 'Negombo', 'Bandaranaike Airport',
 18, NULL, NULL,
 14000.00, NULL, 8000.00,
 'CANCELLED', NULL),

('Dhanushka', 'Kumara', '0767890123', 'dhanushka.kumara@gmail.com',
 '33 Lake Road', 'Colombo',
 '2025-10-27 05:30:00', '2025-10-27 19:00:00',
 'Colombo', 'Ella',
 40, NULL, NULL,
 45000.00, NULL, 8000.00,
 'CANCELLED', NULL),

-- REJECTED BOOKINGS

('Sanduni', 'Jayasinghe', '0758901234', 'sanduni.j@gmail.com',
 '21 Main Street', 'Colombo',
 '2025-10-09 06:00:00', '2025-10-09 18:00:00',
 'Colombo', 'Galle',
 45, NULL, NULL,
 30000.00, NULL, 8000.00,
 'REJECTED', NULL),

('Chamara', 'Wijesinghe', '0749012345', 'chamara.w@gmail.com',
 '56 Station Road', 'Kandy',
 '2025-10-16 05:00:00', '2025-10-16 21:00:00',
 'Kandy', 'Colombo',
 50, NULL, NULL,
 38000.00, NULL, 8000.00,
 'REJECTED', NULL),

('Nethmi', 'Rathnayake', '0780123456', 'nethmi.r@gmail.com',
 '90 Park Avenue', 'Colombo',
 '2025-10-23 07:00:00', '2025-10-23 17:00:00',
 'Colombo', 'Dambulla',
 42, NULL, NULL,
 33000.00, NULL, 8000.00,
 'REJECTED', NULL),

('Isuru', 'Bandara', '0711234567', 'isuru.bandara@gmail.com',
 '15 School Road', 'Kurunegala',
 '2025-10-29 06:00:00', '2025-10-29 19:00:00',
 'Kurunegala', 'Sigiriya',
 48, NULL, NULL,
 36000.00, NULL, 8000.00,
 'REJECTED', NULL);
 
 INSERT INTO Payment
(
    BookingID,
    Amount,
    PaymentDate,
    PaymentMethod,
    PaymentStatus,
    TransactionReference
)
VALUES

-- COMPLETED BOOKINGS

(1, 8000.00, '2025-10-01 10:15:00', 'Bank Transfer', 'SUCCESS', 'TXN-20251001-001'),
(2, 8000.00, '2025-10-01 11:30:00', 'Card', 'SUCCESS', 'TXN-20251001-002'),
(3, 8000.00, '2025-10-02 09:45:00', 'Cash', 'SUCCESS', 'TXN-20251002-003'),
(4, 8000.00, '2025-10-02 14:20:00', 'Bank Transfer', 'SUCCESS', 'TXN-20251002-004'),
(5, 8000.00, '2025-10-03 10:10:00', 'Card', 'SUCCESS', 'TXN-20251003-005'),

(6, 8000.00, '2025-10-05 13:25:00', 'Cash', 'SUCCESS', 'TXN-20251005-006'),
(7, 8000.00, '2025-10-06 15:40:00', 'Bank Transfer', 'SUCCESS', 'TXN-20251006-007'),
(8, 8000.00, '2025-10-08 09:30:00', 'Card', 'SUCCESS', 'TXN-20251008-008'),
(9, 8000.00, '2025-10-09 16:15:00', 'Bank Transfer', 'SUCCESS', 'TXN-20251009-009'),
(10, 8000.00, '2025-10-10 11:50:00', 'Cash', 'SUCCESS', 'TXN-20251010-010'),

(11, 8000.00, '2025-10-12 10:20:00', 'Card', 'SUCCESS', 'TXN-20251012-011'),
(12, 8000.00, '2025-10-13 14:45:00', 'Bank Transfer', 'SUCCESS', 'TXN-20251013-012'),
(13, 8000.00, '2025-10-15 12:10:00', 'Cash', 'SUCCESS', 'TXN-20251015-013'),
(14, 8000.00, '2025-10-16 09:35:00', 'Card', 'SUCCESS', 'TXN-20251016-014'),
(15, 8000.00, '2025-10-18 15:25:00', 'Bank Transfer', 'SUCCESS', 'TXN-20251018-015'),

(16, 8000.00, '2025-10-20 10:40:00', 'Cash', 'SUCCESS', 'TXN-20251020-016'),
(17, 8000.00, '2025-10-22 13:15:00', 'Card', 'SUCCESS', 'TXN-20251022-017'),
(18, 8000.00, '2025-10-24 11:05:00', 'Bank Transfer', 'SUCCESS', 'TXN-20251024-018'),
(19, 8000.00, '2025-10-26 14:30:00', 'Cash', 'SUCCESS', 'TXN-20251026-019'),
(20, 8000.00, '2025-10-27 16:45:00', 'Card', 'SUCCESS', 'TXN-20251027-020'),

-- CANCELLED BOOKINGS - REFUNDED

(21, 8000.00, '2025-10-04 10:00:00', 'Bank Transfer', 'REFUNDED', 'TXN-20251004-021'),
(22, 8000.00, '2025-10-11 12:30:00', 'Card', 'REFUNDED', 'TXN-20251011-022'),
(23, 8000.00, '2025-10-19 09:15:00', 'Cash', 'REFUNDED', 'TXN-20251019-023'),
(24, 8000.00, '2025-10-25 14:00:00', 'Bank Transfer', 'REFUNDED', 'TXN-20251025-024'),

-- REJECTED BOOKINGS - FAILED PAYMENTS

(25, 8000.00, '2025-10-08 11:20:00', 'Card', 'FAILED', 'TXN-20251008-025'),
(26, 8000.00, '2025-10-15 15:10:00', 'Bank Transfer', 'FAILED', 'TXN-20251015-026'),
(27, 8000.00, '2025-10-21 10:45:00', 'Card', 'FAILED', 'TXN-20251021-027');
 
TRUNCATE TABLE Booking;
SELECT * FROM Booking;

TRUNCATE TABLE Payment;
SELECT * FROM Payment;

/* Monthly Booking Summary */

SELECT
    COUNT(*) AS TotalBookings,

    COALESCE(SUM(BookingStatus = 'PENDING'), 0) AS PendingBookings,
    COALESCE(SUM(BookingStatus = 'PAYMENT_PENDING'), 0) AS PaymentPendingBookings,
    COALESCE(SUM(BookingStatus = 'CONFIRMED'), 0) AS ConfirmedBookings,
    COALESCE(SUM(BookingStatus = 'COMPLETED'), 0) AS CompletedBookings,
    COALESCE(SUM(BookingStatus = 'REJECTED'), 0) AS RejectedBookings,
    COALESCE(SUM(BookingStatus = 'CANCELLED'), 0) AS CancelledBookings

FROM Booking
WHERE CreatedAt >= '2026-10-01' AND CreatedAt < '2026-11-01';

/* Monthly Payment Report */
SELECT
    COUNT(DISTINCT BookingID) AS PaidBookings,
    COALESCE(SUM(Amount), 0) AS TotalPaymentsReceived
FROM Payment
WHERE PaymentStatus = 'SUCCESS' AND PaymentDate >= '2025-10-01' AND PaymentDate < '2025-11-01';

/* Complete Monthly Financial Summary */
SELECT
    (
        SELECT COALESCE(SUM(j.IncomeAmount), 0)
        FROM Journey j
        WHERE j.JourneyDate >= '2025-10-01'
          AND j.JourneyDate < '2025-11-01'
    ) AS TotalIncome,

    (
        SELECT COALESCE(SUM(f.FCost), 0)
        FROM FuelLog f
        WHERE f.FDate >= '2025-10-01'
          AND f.FDate < '2025-11-01'
    ) AS FuelExpenses,

    (
        SELECT COALESCE(SUM(m.Cost), 0)
        FROM MaintenanceLog m
        WHERE m.MaintenanceDate >= '2025-10-01'
          AND m.MaintenanceDate < '2025-11-01'
    ) AS MaintenanceExpenses,

    (
        SELECT COALESCE(SUM(e.Amount), 0)
        FROM ExpenseLog e
        WHERE e.Expensedate >= '2025-10-01'
          AND e.Expensedate < '2025-11-01'
    ) AS OtherExpenses,

    (
        SELECT COALESCE(SUM(
            d.BaseMonthlySalary +
            (
                SELECT COUNT(*)
                FROM Journey j
                WHERE j.DriverID = d.DriverID
                  AND j.JourneyDate >= '2025-10-01'
                  AND j.JourneyDate < '2025-11-01'
            ) * d.PerTripAllowence
        ), 0)
        FROM Driver d
        WHERE d.DActive = TRUE
    ) AS DriverSalaries;
    
/* Income vs Expenses */
SELECT
    'Income' AS Category,
    COALESCE(SUM(IncomeAmount), 0) AS Amount
FROM Journey
WHERE JourneyDate >= '2025-10-01'
  AND JourneyDate < '2025-11-01'

UNION ALL

SELECT
    'Fuel' AS Category,
    COALESCE(SUM(FCost), 0) AS Amount
FROM FuelLog
WHERE FDate >= '2025-10-01'
  AND FDate < '2025-11-01'

UNION ALL

SELECT
    'Maintenance' AS Category,
    COALESCE(SUM(Cost), 0) AS Amount
FROM MaintenanceLog
WHERE MaintenanceDate >= '2025-10-01'
  AND MaintenanceDate < '2025-11-01'

UNION ALL

SELECT
    'Other Expenses' AS Category,
    COALESCE(SUM(Amount), 0) AS Amount
FROM ExpenseLog
WHERE Expensedate >= '2025-10-01'
  AND Expensedate < '2025-11-01';