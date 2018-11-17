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

public class DelayStationDB
{
	private static final Logger LOGGER = Logger.getLogger(DelayStationDB.class.getName());

	private final double maximum;
	private final double average;
	private final String stationName;

	public DelayStationDB(double delayAverage, double delayMaximum, String stationName)
	{
		this.average = delayAverage;
		this.maximum = delayMaximum;
		this.stationName = stationName;
	}

	public double getMaximum()
	{
		return maximum;
	}

	public double getAverage()
	{
		return average;
	}

	public String getStationName()
	{
		return stationName;
	}

	private static final Optional<DelayStationDB> getDelayLine(ResultSet result)
	{
		try
		{
			final double delayMaximum = result.getDouble("delay_max");
			final double delayAverage = result.getDouble("delay_avg");
			final String name = result.getString("name");

			return Optional.of(new DelayStationDB(delayAverage, delayMaximum, name));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to DelayStationDB.", e);
			return Optional.empty();
		}
	}

	public static final List<DelayStationDB> getDelays() throws IOException
	{
		final String sql = "SELECT " + "name, "
				+ "avg(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_avg, "
				+ "max(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_max "
				+ "FROM Stop, Station WHERE realTime IS NOT NULL AND Stop.stationID = Station.stationID GROUP BY Station.stationID ORDER BY delay_avg DESC LIMIT 20;";
		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayStationDB> list = new ArrayList<>();
			database.select(r -> DelayStationDB.getDelayLine(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
