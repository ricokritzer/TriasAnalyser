package de.dhbw.studienarbeit.data.reader.database;

public class DelayWeatherDBHelper
{
	private DelayWeatherDBHelper()
	{}

	public static String getSQL(final String what)
	{
		return new StringBuilder().append("SELECT max(delay) AS delay_max, avg(delay) AS delay_avg, ").append(what) //
				.append(" FROM (SELECT stopID, ").append("avg(delay) AS delay, ") //
				.append("ROUND(avg(temp), 1) AS temp, ") //
				.append("ROUND(avg(humidity),1) AS humidity, ") //
				.append("ROUND(avg(wind),1) AS wind, ") //
				.append("ROUND(avg(pressure),1) AS pressure, ") //
				.append("ROUND(avg(clouds),1) AS clouds ") //
				.append("FROM (") //
				.append("SELECT stopID, realTime, ") //
				.append("(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay, ") //
				.append("ROUND(Station.lat, 2) AS lat, ") //
				.append("ROUND (Station.lon, 2) AS lon ") //
				.append("FROM Stop, Station ") //
				.append("WHERE Stop.stationID = Station.stationID AND realTime IS NOT NULL") //
				.append(") AS Stop_Coordinates, Weather ") //
				.append("WHERE Weather.lat = Stop_Coordinates.lat ") //
				.append("AND Weather.lon = Stop_Coordinates.lon ") //
				.append("AND Weather.timeStamp < DATE_ADD(Stop_Coordinates.realTime,INTERVAL 15 MINUTE) ") //
				.append("AND Weather.timeStamp > DATE_SUB(Stop_Coordinates.realTime,INTERVAL 45 MINUTE) ") //
				.append("GROUP BY stopID) AS Stop_Weather ") //
				.append("GROUP BY ") //
				.append(what).append(";") //
				.toString();
	}
}
