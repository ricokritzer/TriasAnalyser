SELECT 
        avg(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_avg,
        max(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_max,
        WeatherIcon.textDE,
        WeatherIcon.icon
FROM Stop, Weather, Station, WeatherIcon
WHERE 
ROUND(Station.lat, 2) = Weather.lat AND ROUND(Station.lon, 2) = Weather.lon AND
Stop.realTime < DATE_ADD(Weather.timeStamp,INTERVAL 5 MINUTE) AND
Stop.realTime > DATE_SUB(Weather.timeStamp,INTERVAL 5 MINUTE) AND
Station.stationID = Stop.stationID AND 
WeatherIcon.text = Weather.text
GROUP BY WeatherIcon.textDE, WeatherIcon.icon;