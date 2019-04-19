DROP TABLE Station;
CREATE TABLE Station (
        stationID varchar(20),
        name varchar(80),
        lat decimal(24,16),
        lon decimal(24,16),
        operator varchar (8),
        observe boolean default false,
        
        PRIMARY KEY (stationID));
        
ALTER TABLE Station ADD FOREIGN KEY (operator) REFERENCES Operator(operator) ON DELETE CASCADE; 