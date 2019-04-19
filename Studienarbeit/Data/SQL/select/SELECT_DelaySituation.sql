SELECT 
        text,
        avg(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay
FROM
        StopSituation,
        Stop,
        Situation
        
WHERE
        StopSituation.stopID = Stop.stopID AND
        StopSituation.situationID = Situation.situationID AND StopSituation.version = Situation.version

GROUP BY text
;