SELECT DISTINCT
        stationID1, stationID2
FROM StationNeighbour;

SELECT avg(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay
FROM Stop
WHERE Stop.lineID in (SELECT LineId FROM StationNeighbour WHERE stationID1 = 'de:07334:1721' AND stationID2 = 'de:07334:1723')
AND Stop.stationID = 'de:07334:1721';

SELECT avg(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay
FROM Stop
WHERE Stop.lineID in (SELECT LineId FROM StationNeighbour WHERE stationID1 = 'de:07334:1721' AND stationID2 = 'de:07334:1723')
AND Stop.stationID = 'de:07334:1723';
        