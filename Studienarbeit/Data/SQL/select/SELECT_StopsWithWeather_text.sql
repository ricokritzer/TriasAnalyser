SELECT
        avg(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_avg,
        max(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_max,
        WeatherIcon.textDE,
        WeatherIcon.icon
FROM StopWeather, Stop, Weather, Station, WeatherIcon
WHERE Stop.stopID = StopWeather.stopID
                AND Stop.stationID = Station.stationID
                AND Weather.lat = ROUND(Station.lat, 2)
                AND Weather.lon = ROUND(Station.lon, 2)
                AND Weather.timeStamp = StopWeather.timeStamp
                AND WeatherIcon.text = Weather.text
GROUP BY WeatherIcon.textDE, WeatherIcon.icon
        