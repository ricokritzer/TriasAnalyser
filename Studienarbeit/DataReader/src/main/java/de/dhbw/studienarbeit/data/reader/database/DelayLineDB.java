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

public class DelayLineDB
{
	private static final Logger LOGGER = Logger.getLogger(DelayLineDB.class.getName());

	private final double maximum;
	private final double average;
	private final String lineName;
	private final String lineDestination;

	public DelayLineDB(double delayAverage, double delayMaximum, String lineName, String lineDestination)
	{
		this.average = delayAverage;
		this.maximum = delayMaximum;
		this.lineName = lineName;
		this.lineDestination = lineDestination;
	}

	public double getMaximum()
	{
		return maximum;
	}

	public double getAverage()
	{
		return average;
	}

	public String getLineName()
	{
		return lineName;
	}

	public String getLineDestination()
	{
		return lineDestination;
	}

	public static final String getSQL()
	{
		return "SELECT " + "name, destination, "
				+ "avg(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_avg, "
				+ "max(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_max "
				+ "FROM Stop, Line WHERE realTime IS NOT NULL AND Stop.lineID = Line.lineID GROUP BY Stop.lineID;";
	}

	public static final Optional<DelayLineDB> getDelayLine(ResultSet result)
	{
		try
		{
			final double delayMaximum = result.getDouble("delay_max");
			final double delayAverage = result.getDouble("delay_avg");
			final String name = result.getString("name");
			final String destination = result.getString("destination");

			return Optional.of(new DelayLineDB(delayAverage, delayMaximum, name, destination));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to stop.", e);
			return Optional.empty();
		}
	}

	public static final List<DelayLineDB> getDelaysByLineName() throws IOException
	{
		final String sql = "SELECT " + "name, destination, "
				+ "avg(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_avg, "
				+ "max(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_max "
				+ "FROM Stop, Line WHERE realTime IS NOT NULL AND Stop.lineID = Line.lineID GROUP BY Stop.lineID;";
		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayLineDB> list = new ArrayList<>();
			database.select(r -> DelayLineDB.getDelayLine(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
