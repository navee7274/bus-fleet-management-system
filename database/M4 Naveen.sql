USE bus_fleet_management_001;

-- Booking Table

CREATE TABLE Booking (
    BookingID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerName VARCHAR(100) NOT NULL,
    CustomerContact VARCHAR(30) NOT NULL,
    StartDateTime DATETIME NOT NULL,
    EndDateTime DATETIME NOT NULL,
    StartLocation VARCHAR(255) NOT NULL,
    Destination VARCHAR(255) NOT NULL,
    PassengerCount INT NOT NULL,
    BRegistrationNo VARCHAR(20),
    EstimatedCost DECIMAL(12,2),
    FinalPrice DECIMAL(12,2),
    BookingStatus ENUM(
        'Temporary',
        'Pending Payment',
        'Confirmed',
        'Cancelled',
        'Completed'
    ) NOT NULL DEFAULT 'Temporary',
    CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_booking_bus
        FOREIGN KEY (BRegistrationNo)
        REFERENCES Bus(BRegistrationNo),
    CONSTRAINT chk_booking_passengers
        CHECK (PassengerCount > 0),
    CONSTRAINT chk_booking_datetime
        CHECK (StartDateTime > StartDateTime),
    CONSTRAINT chk_booking_estimated_cost
        CHECK (EstimatedCost IS NULL OR EstimatedCost >= 0),
    CONSTRAINT chk_booking_final_price
        CHECK (FinalPrice IS NULL OR FinalPrice >= 0)
);


CREATE TABLE Payment (
    PaymentID INT AUTO_INCREMENT PRIMARY KEY,
    BookingID INT NOT NULL,
    Amount DECIMAL(12,2) NOT NULL,
	PaymentDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PaymentMethod VARCHAR(50),
    PaymentStatus ENUM(
        'Pending',
        'Paid',
        'Failed',
        'Refunded'
    ) NOT NULL DEFAULT 'Pending',
    TransactionReference VARCHAR(100) UNIQUE,

    CONSTRAINT fk_payment_booking
        FOREIGN KEY (BookingID)
        REFERENCES Booking(BookingID),
    CONSTRAINT chk_payment_amount
        CHECK (Amount > 0)
);

DROP TABLE MaintenanceLog;