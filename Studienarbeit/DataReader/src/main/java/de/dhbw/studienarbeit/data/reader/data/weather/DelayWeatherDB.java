package de.dhbw.studienarbeit.data.reader.data.weather;

import de.dhbw.studienarbeit.data.reader.data.DelayDB;

public abstract class DelayWeatherDB<T> extends DelayDB<T>
{
	public static String buildSQL(final String what, final String name)
	{
		return new StringBuilder().append("SELECT ").append("count(*) AS total, ")
				.append("avg(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_avg, ")
				.append("max(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_max, ")
				.append(what).append(" AS ").append(name).append(" FROM StopWeather, Stop, Weather ")
				.append("WHERE Stop.stopID = StopWeather.stopID AND StopWeather.weatherId = Weather.id ")
				.append("AND realTime IS NOT NULL ").append("GROUP BY ").append(name).append(" ORDER BY ").append(name)
				.append(";").toString();
	}
}
