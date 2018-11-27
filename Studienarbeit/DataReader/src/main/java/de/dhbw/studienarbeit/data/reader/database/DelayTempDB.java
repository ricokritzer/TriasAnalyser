package de.dhbw.studienarbeit.data.reader.database;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DelayTempDB
{
	private static final Logger LOGGER = Logger.getLogger(DelayTempDB.class.getName());
	private static final String WHAT = "temp";

	private final double average;
	private final double maximum;
	private final double value;

	public DelayTempDB(double delayAverage, double delayMaximum, double value)
	{
		this.average = delayAverage;
		this.maximum = delayMaximum;
		this.value = value;
	}

	public double getAverage()
	{
		return average;
	}

	public double getMaximum()
	{
		return maximum;
	}

	public double getValue()
	{
		return value;
	}

	private static final Optional<DelayTempDB> getDelayLine(ResultSet result)
	{
		try
		{
			final double delayMaximum = result.getDouble("delay_max");
			final double delayAverage = result.getDouble("delay_avg");
			final double wind = result.getDouble(WHAT);

			return Optional.of(new DelayTempDB(delayAverage, delayMaximum, wind));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to " + DelayWeatherDB.class.getName(), e);
			return Optional.empty();
		}
	}

	public static final List<DelayTempDB> getDelays() throws IOException
	{
		final String sql = "SELECT max(delay) AS delay_max, avg(delay) AS delay_avg, " + WHAT
				+ " FROM "
				+ "(SELECT "
				+ "stopID, "
				+ "avg(delay) AS delay, "
				+ "avg(temp) AS temp, "
				+ "avg(humidity) AS humidity, "
				+ "avg(wind) AS wind, "
				+ "avg(pressure) AS ressure, "
				+ "avg(clouds) AS clouds "
				+ "FROM ("
				+ "SELECT stopID, realTime, "
				+ "(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay, "
				+ "ROUND(Station.lat, 2) AS lat, "
				+ "ROUND (Station.lon, 2) AS lon "
				+ "FROM Stop, Station "
				+ "WHERE Stop.stationID = Station.stationID AND realTime IS NOT NULL"
				+ ") AS Stop_Coordinates, Weather "
				+ "WHERE Weather.lat = Stop_Coordinates.lat "
				+ "AND Weather.lon = Stop_Coordinates.lon "
				+ "AND Weather.timeStamp < DATE_ADD(Stop_Coordinates.realTime,INTERVAL 15 MINUTE) "
				+ "AND Weather.timeStamp > DATE_SUB(Stop_Coordinates.realTime,INTERVAL 45 MINUTE) "
				+ "GROUP BY stopID) AS Stop_Weather "
				+ "GROUP BY "
				+ WHAT + ";";

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayTempDB> list = new ArrayList<>();
			database.select(r -> DelayTempDB.getDelayLine(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
