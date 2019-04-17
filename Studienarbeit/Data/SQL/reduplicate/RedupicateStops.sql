SELECT count(*) AS befor_Delete FROM Stop;

CREATE TABLE stopsToDelete(
        stopID int NOT NULL PRIMARY KEY
);

INSERT INTO stopsToDelete SELECT min(Stop.stopID)
        FROM Stop, (SELECT count(*) AS total, lineID, timeTabledTime, stationID FROM Stop GROUP BY lineID, timeTabledTime, stationID HAVING total > 1) s
        WHERE Stop.lineID = s.lineID AND Stop.timeTabledTime = s.timeTabledTime AND Stop.stationID = s.stationID
        GROUP BY Stop.lineID, Stop.timeTabledTime, Stop.stationID;
        
DELETE FROM Stop WHERE stopID IN (SELECT stopID FROM stopsToDelete);

DROP TABLE stopsToDelete;
         
 SELECT count(*) AS after_Delete FROM Stop;