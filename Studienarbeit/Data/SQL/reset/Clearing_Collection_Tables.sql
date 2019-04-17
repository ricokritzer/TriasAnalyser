DROP TABLE Line;
CREATE TABLE Line (
        lineID int NOT NULL AUTO_INCREMENT, 
        name varchar(50),
        destination varchar(150),
        
        PRIMARY KEY (lineID)
        );
        
DROP TABLE Stop;
CREATE TABLE Stop (
        stopID int NOT NULL AUTO_INCREMENT, 
        stationID varchar(20),
        lineID int NOT NULL,
        timeTabledTime datetime,
        realTime datetime,
        
        PRIMARY KEY (stopID)
        );
        
DROP TABLE Weather;
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
        
        primary key (lat, lon, timeStamp)
        );
        
DROP TABLE Monitor;
CREATE TABLE Monitor (
        monitorID int NOT NULL AUTO_INCREMENT,
        name varchar (8),
        timeStamp timestamp,
        number int,
        
        PRIMARY KEY (monitorID, name)
        );