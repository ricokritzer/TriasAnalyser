INSERT INTO Stop (stationID, lineID, timeTabledTime, realTime) 
        VALUES (?, (SELECT lineID FROM Line WHERE name = ? AND destination = ?), ?, ?);