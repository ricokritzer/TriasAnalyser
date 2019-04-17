DROP TABLE StationNeighbour;

CREATE TABLE StationNeighbour(
        stationNeighbourId int AUTO_INCREMENT,
        stationID1 varchar(20),
        stationID2 varchar(20),
        lineID int,
       
        PRIMARY KEY (stationNeighbourId)
);

ALTER TABLE StationNeighbour ADD FOREIGN KEY StationNeighbour_Station_1 (stationID1) REFERENCES Station(stationID) ON DELETE CASCADE;
ALTER TABLE StationNeighbour ADD FOREIGN KEY StationNeighbour_Station_2 (stationID2) REFERENCES Station(stationID) ON DELETE CASCADE;
ALTER TABLE StationNeighbour ADD FOREIGN KEY StationNeighbour_Line (lineID) REFERENCES Line(lineID) ON DELETE CASCADE;