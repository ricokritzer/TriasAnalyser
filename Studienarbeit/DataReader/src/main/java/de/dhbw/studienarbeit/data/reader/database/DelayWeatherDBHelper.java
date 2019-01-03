package de.dhbw.studienarbeit.data.reader.database;

public class DelayWeatherDBHelper
{
	private DelayWeatherDBHelper()
	{}

	public static String buildSQL(final String what, final String name)
	{
		return new StringBuilder().append("SELECT ")
				.append("avg(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_avg, ")
				.append("max(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_max, ")
				.append(what).append(" AS ").append(name).append(" FROM StopWeather, Stop, Weather, Station ")
				.append("WHERE Stop.stopID = StopWeather.stopID AND Stop.stationID = Station.stationID ")
				.append("AND Weather.lat = ROUND(Station.lat, 2) AND Weather.lon = ROUND(Station.lon, 2) ")
				.append(" AND Weather.timeStamp = StopWeather.timeStamp ")

				.append("GROUP BY ").append(name).append(" ORDER BY delay_avg DESC;").toString();
	}
}
