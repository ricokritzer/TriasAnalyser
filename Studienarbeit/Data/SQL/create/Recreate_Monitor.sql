DROP TABLE Monitor;
CREATE TABLE Monitor (
        monitorID int NOT NULL AUTO_INCREMENT,
        name varchar (8),
        timeStamp timestamp,
        number int,
        
        PRIMARY KEY (monitorID, name)
        );