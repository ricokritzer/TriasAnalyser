package de.dhbw.studienarbeit.data.reader.database;

public class DelayWeatherCorrelationHelper
{
	public static final String getSqlFor(String what)
	{
		return new StringBuilder("SELECT ").append(what)
				.append(", (UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay ")
				.append("FROM StopWeather, Stop, Weather, Station ")
				.append("WHERE Stop.stopID = StopWeather.stopID AND Stop.stationID = Station.stationID ")
				.append("AND Weather.lat = ROUND(Station.lat, 2) AND Weather.lon = ROUND(Station.lon, 2) ")
				.append("AND Weather.timeStamp = StopWeather.timeStamp;").toString();
	}
}
