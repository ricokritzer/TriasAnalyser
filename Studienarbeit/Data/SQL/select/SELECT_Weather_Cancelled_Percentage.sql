SELECT total, cancelled, t.v FROM 
(SELECT count(*) AS total, ROUND(temp, 0) AS v FROM Stop, StopWeather, Weather WHERE Stop.stopID = StopWeather.stopID AND Weather.id = StopWeather.weatherID GROUP BY v) t, 
(SELECT count(*) AS cancelled, ROUND(temp, 0) AS v FROM Stop, StopWeather, Weather WHERE Stop.stopID = StopWeather.stopID AND Weather.id = StopWeather.weatherID AND realtime IS NULL GROUP BY v)c
WHERE t.v = c.v;