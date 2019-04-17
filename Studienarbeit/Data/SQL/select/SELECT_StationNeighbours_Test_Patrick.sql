SELECT 
        station1.stationID AS StationID1,
        station1.name AS StationName1,
        station2.stationID AS StationID2,
        station2.name AS StationName2,
        Line.lineID AS LineID,
        Line.name AS LineName,
        Line.destination AS LineDestination

FROM 
        StationNeighbour, Station station1, Station station2, Line
WHERE 
        StationNeighbour.stationID1 = station1.stationID
        AND StationNeighbour.stationID2 = station2.stationID
        AND StationNeighbour.lineID = Line.lineID;
