ALTER TABLE StopWeather ADD weatherId int;
ALTER TABLE StopWeather ADD FOREIGN KEY Stop_Weather (weatherId) REFERENCES Weather(id) ON DELETE CASCADE; 