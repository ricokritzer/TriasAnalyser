EXPLAIN SELECT sum((x-avgX) * (y-avgY)) / SQRT(sum(POW(x-avgX,2))*sum(POW(y-avgY,2)))

FROM

(SELECT Weather.temp AS x, UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime) AS y
FROM StopWeather, Stop, Weather WHERE StopWeather.stopID = Stop.stopID AND StopWeather.weatherID = Weather.id) t,

(SELECT avg(temp) AS avgX FROM StopWeather, Weather WHERE StopWeather.weatherID = Weather.id) tX,

(SELECT avg(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS avgY FROM StopWeather, Stop WHERE StopWeather.stopID = Stop.stopID) tY;