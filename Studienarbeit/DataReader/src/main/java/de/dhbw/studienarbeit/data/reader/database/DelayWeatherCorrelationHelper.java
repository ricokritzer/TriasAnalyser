package de.dhbw.studienarbeit.data.reader.database;

public class DelayWeatherCorrelationHelper
{
	public static final String getSqlFor(String what)
	{
		return new StringBuilder("SELECT ").append(what)
				.append(", (UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay ")
				.append("FROM StopWeather, Stop, Weather ")
				.append("WHERE Stop.stopID = StopWeather.stopID AND StopWeather.weatherId = Weather.id;").toString();
	}
}
