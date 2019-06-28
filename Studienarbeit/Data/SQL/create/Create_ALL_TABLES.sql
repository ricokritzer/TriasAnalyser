CREATE TABLE Api (
        apiID int NOT NULL AUTO_INCREMENT,
        name varchar (8),
        apiKey varchar (80),
        maximumRequests int,
        url varchar (80),
        
        PRIMARY KEY (apiID)
        );

        
CREATE TABLE Monitor (
        monitorID int NOT NULL AUTO_INCREMENT,
        name varchar (8),
        timeStamp timestamp,
        number int,
        
        PRIMARY KEY (monitorID, name)
        );


CREATE TABLE Situation (
        situationID varchar(25) NOT NULL, 
        version int NOT NULL,
        text text,
        
        PRIMARY KEY (situationID, version)
        );


CREATE TABLE Station (
        stationID varchar(20),
        name varchar(80),
        lat decimal(24,16),
        lon decimal(24,16),
        operator varchar (8),
        observe boolean default false,
        
        PRIMARY KEY (stationID)
        );
        

CREATE TABLE StationNeighbour(
        stationID1 varchar(20),
        stationID2 varchar(20),
       
        PRIMARY KEY (stationID1, stationID2)
        );


CREATE TABLE Stop(
        stopID int NOT NULL AUTO_INCREMENT, 
        stationID varchar(20),
        lineID int NOT NULL,
        timeTabledTime datetime,
        realTime datetime,

        PRIMARY KEY (stopID)
        );


CREATE TABLE StopSituation (
        situationID varchar(25),
        version int,
        stopID int,
        
        PRIMARY KEY (situationID, version, stopID)
        );


CREATE TABLE Weather (
        lat decimal(8,2),
        lon decimal(8,2),
        timeStamp timestamp,
        temp decimal(6,2),
        humidity decimal(6,2),
        pressure decimal(6,2),
        wind decimal(6,2),
        clouds decimal(6,2),
        text varchar(50),
        
        PRIMARY KEY (lat, lon, timeStamp)
        );


CREATE TABLE Operator(
        operator varchar (8),
        contact varchar(50),
        displayName varchar(50),
        
        PRIMARY KEY(operator)
);


ALTER TABLE StopSituation ADD FOREIGN KEY StopSituation_Situation (situationID, version) REFERENCES Situation(situationID, version) ON DELETE CASCADE; 
ALTER TABLE StopSituation ADD FOREIGN KEY StopSituation_Stop (stopID) REFERENCES Stop(stopID) ON DELETE CASCADE; 

ALTER TABLE Stop ADD FOREIGN KEY Stop_Station (stationID) REFERENCES Station(stationID) ON DELETE CASCADE; 
ALTER TABLE Stop ADD FOREIGN KEY Stop_Line (lineID) REFERENCES Line(lineID) ON DELETE CASCADE; 

ALTER TABLE StationNeighbour ADD FOREIGN KEY StationNeighbour_Station_1 (stationID1) REFERENCES Station(stationID) ON DELETE CASCADE;
ALTER TABLE StationNeighbour ADD FOREIGN KEY StationNeighbour_Station_2 (stationID2) REFERENCES Station(stationID) ON DELETE CASCADE;

ALTER TABLE Station ADD FOREIGN KEY (operator) REFERENCES Operator(operator) ON DELETE CASCADE; 