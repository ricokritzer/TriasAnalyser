package de.dhbw.studienarbeit.data.reader.database;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Count
{
	private static final Logger LOGGER = Logger.getLogger(Count.class.getName());

	public static final Count UNABLE_TO_COUNT = new Count(-1);

	protected long value;

	public Count(long value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return Long.toString(value);
	}

	public static final Optional<Count> getCount(ResultSet result)
	{
		try
		{
			return Optional.ofNullable(new Count(result.getLong("total")));
		}
		catch (SQLException e)
		{
			return Optional.empty();
		}
	}

	public static final Count countStops()
	{
		try
		{
			return new DatabaseReader().count("Stop");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count stops.", e);
			return Count.UNABLE_TO_COUNT;
		}
	}

	public static final Count countWeather()
	{
		try
		{
			return new DatabaseReader().count("Weather");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count weather.", e);
			return Count.UNABLE_TO_COUNT;
		}
	}

	public static final Count countStations()
	{
		try
		{
			return new DatabaseReader().count("Station");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count weather.", e);
			return Count.UNABLE_TO_COUNT;
		}
	}

	public static final Count countLines()
	{
		try
		{
			return new DatabaseReader().count("Line");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count lines.", e);
			return Count.UNABLE_TO_COUNT;
		}
	}
}
