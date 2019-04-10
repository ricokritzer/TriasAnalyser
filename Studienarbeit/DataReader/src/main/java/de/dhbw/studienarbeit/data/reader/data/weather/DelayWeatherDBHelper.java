package de.dhbw.studienarbeit.data.reader.data.weather;

public class DelayWeatherDBHelper
{
	private DelayWeatherDBHelper()
	{}

	public static String buildSQLCountCancelledStops(final String what, final String name)
	{
		return new StringBuilder().append("SELECT ").append("count(*) AS total, ").append(what).append(" AS ")
				.append(name).append(" FROM StopWeather, Stop, Weather ")
				.append("WHERE Stop.stopID = StopWeather.stopID AND StopWeather.weatherId = Weather.id ")
				.append("AND realTime IS NULL ").append("GROUP BY ").append(name).append(" ORDER BY ").append(name)
				.append(";").toString();
	}
}
