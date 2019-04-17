SELECT
        avg(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_avg,
        max(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_max,
        ROUND(temp,0) AS temp_rounded
FROM Stop, Weather, Station
WHERE 
        ROUND(Station.lat, 2) = Weather.lat AND ROUND(Station.lon, 2) = Weather.lon AND
        Stop.realTime < DATE_ADD(Weather.timeStamp,INTERVAL 10 MINUTE) AND Stop.realTime > DATE_SUB(Weather.timeStamp,INTERVAL 10 MINUTE) AND
        Stop.stationID = Station.stationID
GROUP BY temp_rounded;