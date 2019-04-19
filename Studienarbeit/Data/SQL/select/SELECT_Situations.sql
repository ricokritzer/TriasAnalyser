SELECT situationID, max(version), text FROM Situation GROUP BY situationID, text;

SELECT avg(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS avg_delay, count(*) AS total,  (stopID IN (SELECT stopID FROM StopSituation WHERE situationID = '110003651_KVV_ICSKVV')) AS withSituation
FROM Stop WHERE lineID IN (SELECT DISTINCT lineID FROM StopSituation, Stop WHERE Stop.stopID = StopSituation.stopID AND StopSituation.situationID = '110003651_KVV_ICSKVV')
GROUP BY withSituation ORDER BY withSituation;