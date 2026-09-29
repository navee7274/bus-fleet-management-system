USE bus_fleet_management_001;

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

