CREATE TABLE i(
        icon char(3),
        cancelled decimal(6),
        total decimal(8),
        percentage decimal(8,4)
);

INSERT INTO i (total, icon) SELECT count(*), icon FROM Weather, StopWeather, Stop, WeatherInformation 
        WHERE Weather.id = StopWeather.weatherID AND StopWeather.stopID = Stop.stopID AND Weather.weatherInformationID = WeatherInformation.id GROUP BY icon ORDER BY icon;
        
UPDATE i SET cancelled = (SELECT count(*) FROM Weather, StopWeather, Stop, WeatherInformation 
        WHERE realtime IS NULL AND Weather.id = StopWeather.weatherID AND StopWeather.stopID = Stop.stopID AND WeatherInformation.id = Weather.weatherInformationID AND WeatherInformation.icon = i.icon);

UPDATE i SET percentage = cancelled/total;

SELECT * FROM i;

DROP TABLE i;