SELECT
        avg(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_avg,
        max(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_max,
        ROUND (temp,0) AS temp_round
FROM StopWeather, Stop, Weather
WHERE Stop.stopID = StopWeather.stopID AND Weather.id = StopWeather.weatherID
GROUP BY temp_round
        