SELECT 
        max(delay) AS delay_max,
        avg(delay) AS delay_avg,
        wind,
        pressure
FROM
(
SELECT
        stopID,
        avg(delay) AS delay,
        avg(temp) AS temp,
        avg(humidity) AS humidity,
        avg(wind) AS wind,
        avg(pressure) AS pressure,
        avg(clouds) AS clouds
        
        FROM
        (SELECT 
                stopID,
                realTime,
                (UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay,
                ROUND(Station.lat, 2) AS lat,
                ROUND(Station.lon, 2) AS lon
                FROM Stop, Station
                WHERE Stop.stationID = Station.stationID AND realTime IS NOT NULL
        ) AS Stop_Coordinates,
        Weather
        
        WHERE
                Weather.lat = Stop_Coordinates.lat 
                AND Weather.lon = Stop_Coordinates.lon 
                AND Weather.timeStamp < DATE_ADD(Stop_Coordinates.realTime,INTERVAL 15 MINUTE) 
                AND Weather.timeStamp > DATE_SUB(Stop_Coordinates.realTime,INTERVAL 45 MINUTE)
                
GROUP BY stopID
) AS Stop_Weather

GROUP BY wind, pressure
;