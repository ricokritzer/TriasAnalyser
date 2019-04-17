DROP TABLE IF EXISTS StopWeather;
CREATE TABLE StopWeather (
        stopID int NOT NULL,
        watherID int NOT NULL,
        
        primary key (stopID)
        );
       
ALTER TABLE StopWeather ADD FOREIGN KEY StopWeather_Stop (stopID) REFERENCES Stop(stopID) ON DELETE CASCADE;
ALTER TABLE StopWeather ADD FOREIGN KEY Stop_Weather (weatherID) REFERENCES Weather(id) ON DELETE CASCADE; 