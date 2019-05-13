CREATE TABLE t(
        pressure decimal(6) PRIMARY KEY,
        cancelled decimal(6),
        total decimal(6),
        percentage decimal(8,4)
);

INSERT INTO t (total, pressure) SELECT count(*), ROUND(pressure, 0) AS p FROM Weather, StopWeather, Stop 
        WHERE Weather.id = StopWeather.weatherID AND StopWeather.stopID = Stop.stopID GROUP BY p ORDER BY p;
        
DELETE FROM t WHERE total < 10000;
UPDATE t SET cancelled = (SELECT count(*) FROM Weather, StopWeather, Stop WHERE realtime IS NULL AND Weather.id = StopWeather.weatherID AND StopWeather.stopID = Stop.stopID AND ROUND(Weather.pressure,0) = t.pressure);
UPDATE t SET percentage = cancelled/total;

SELECT * FROM t;

DROP TABLE t;