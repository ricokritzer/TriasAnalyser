DROP TABLE Stop;
CREATE TABLE Stop(
        stopID int NOT NULL AUTO_INCREMENT, 
        stationID varchar(20),
        lineID int NOT NULL,
        timeTabledTime datetime,
        realTime datetime,

        PRIMARY KEY (stopID)
        );


ALTER TABLE Stop ADD FOREIGN KEY Stop_Station (stationID) REFERENCES Station(stationID) ON DELETE CASCADE; 
ALTER TABLE Stop ADD FOREIGN KEY Stop_Line (lineID) REFERENCES Line(lineID) ON DELETE CASCADE; 