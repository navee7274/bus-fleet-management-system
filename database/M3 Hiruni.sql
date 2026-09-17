USE bus_fleet_management_001;

CREATE TABLE Journey(
JourneyID INT PRIMARY KEY,
JDate DATE NOT NULL,
JBusNo VARCHAR(10) NOT NULL,
JDriver VARCHAR(5) NOT NULL,
JPurpose VARCHAR(20) NOT NULL,
Jclient VARCHAR(10) ,
JDeparture VARCHAR(50),
JDestination VARCHAR(50),
JStartOdo INT NOT NULL,
JEndOdo INT NOT NULL,
JKMTravelled  INT GENERATED ALWAYS AS (JEndOdo - JStartOdo) STORED,
JIncomeAmount DECIMAL(10,2) NOT NULL,
JNotes VARCHAR(100),
CONSTRAINT ST1 CHECK( JStartOdo > 0),
CONSTRAINT ST2 CHECK( JEndOdo > 0),
CONSTRAINT ST3 CHECK( JEndOdo > JStartOdo ),
CONSTRAINT ST4 CHECK( JPurpose IN('Staff Transport', 'Tour', 'Hire', 'Other'))
);

CREATE TABLE Expence(
Edate DATE NOT NULL,
EbusRegNo VARCHAR(20) NOT NULL,
ECategory VARCHAR(20) NOT NULL,
EPaymentMethod VARCHAR(20) NOT NULL,
EAmount DECIMAL(10,2),
EDescription VARCHAR(50),
CONSTRAINT ET1 CHECK(EAmount > 0),
CONSTRAINT ET2 CHECK( ECategory IN ('Insurance', 'Tires', 'Spare Parts', 'Other Staff Wages', 'Other')),
CONSTRAINT ET3 CHECK( EPaymentMethod IN('Cash', 'Bank Transfer', 'Cheque', 'Credit', 'Other'))
);

 

DESCRIBE Journey;

DROP TABLE Journey;