DROP TABLE Situation;
CREATE TABLE Situation (
        situationID varchar(25) NOT NULL, 
        version int NOT NULL,
        text text,
        
        PRIMARY KEY (situationID, version)
        );