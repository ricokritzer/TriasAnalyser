package de.dhbw.studienarbeit.data.helper.database.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DelayLineDB
{
	private static final Logger LOGGER = Logger.getLogger(DelayLineDB.class.getName());

	private static final String DELAY_MAX = "delay_max";
	private static final String DELAY_AVG = "delay_avg";

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

	public static final Optional<DelayLineDB> getDelayLine(ResultSet result)
	{
		try
		{
			final double delayMaximum = result.getDouble(DELAY_MAX);
			final double delayAverage = result.getDouble(DELAY_AVG);
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
}
