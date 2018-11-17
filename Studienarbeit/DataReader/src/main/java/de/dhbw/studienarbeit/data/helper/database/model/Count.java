package de.dhbw.studienarbeit.data.helper.database.model;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTable;

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
			return new DatabaseTable()
			{
				@Override
				protected String getTableName()
				{
					return "Stop";
				}
			}.count();
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
			return new DatabaseTable()
			{
				@Override
				protected String getTableName()
				{
					return "Weather";
				}
			}.count();
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
			return new DatabaseTable()
			{
				@Override
				protected String getTableName()
				{
					return "Station";
				}
			}.count();
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
			return new DatabaseTable()
			{
				@Override
				protected String getTableName()
				{
					return "Line";
				}
			}.count();
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count lines.", e);
			return Count.UNABLE_TO_COUNT;
		}
	}
}