INSERT INTO StopWeather (stopID, weatherId) SELECT Stop.stopID, max(Weather.id) FROM Stop, Station, Weather
	WHERE Stop.stationID = Station.stationID AND Stop.stopID = ? AND Stop.stopID NOT IN (SELECT stopID FROM StopWeather)
	AND Weather.lat = ROUND(Station.lat, 2) AND Weather.lon = ROUND(Station.lon, 2) 
	AND Weather.timeStamp < DATE_ADD(Stop.realtime, INTERVAL 60 MINUTE)
	GROUP BY Stop.stopID;
	