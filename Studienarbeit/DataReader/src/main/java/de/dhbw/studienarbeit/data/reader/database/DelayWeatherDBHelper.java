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
				.append(what).append(" AS ").append(name).append(" FROM Stop, Weather, Station ").append("WHERE ")
				.append("ROUND(Station.lat, 2) = Weather.lat AND ROUND(Station.lon, 2) = Weather.lon AND ")
				.append("Stop.realTime < DATE_ADD(Weather.timeStamp,INTERVAL 10 MINUTE) AND Stop.realTime > DATE_SUB(Weather.timeStamp,INTERVAL 10 MINUTE) AND ")
				.append("Station.stationID = Stop.stationID ").append("GROUP BY ").append(name).append(" ORDER BY delay_avg DESC;").toString();
	}
}
