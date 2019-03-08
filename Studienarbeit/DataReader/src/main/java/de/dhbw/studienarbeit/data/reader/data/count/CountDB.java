package de.dhbw.studienarbeit.data.reader.data.count;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class CountDB implements Count
{
	private static final Logger LOGGER = Logger.getLogger(CountDB.class.getName());

	public static final CountDB UNABLE_TO_COUNT = new CountDB(-1);

	protected long value;

	public CountDB(long value)
	{
		this.value = value;
	}

	public long getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		return Long.toString(value);
	}

	public static final Optional<CountDB> getCount(ResultSet result)
	{
		try
		{
			return Optional.ofNullable(new CountDB(result.getLong("total")));
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
			return new DatabaseReader().count("SELECT count(*) AS total FROM Stop;");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count stops.", e);
			return CountDB.UNABLE_TO_COUNT;
		}
	}

	public static final Count countWeather()
	{
		try
		{
			return new DatabaseReader().count("SELECT count(*) AS total FROM Weather;");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count weather.", e);
			return CountDB.UNABLE_TO_COUNT;
		}
	}

	public static final Count countStations()
	{
		try
		{
			return new DatabaseReader().count("SELECT count(*) AS total FROM Station;");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count stations.", e);
			return CountDB.UNABLE_TO_COUNT;
		}
	}

	public static final Count countStationsWithRealtimeData()
	{
		try
		{
			return new DatabaseReader().count("SELECT count(*) AS total FROM Station WHERE stopSaved = true;");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count stations with realtime data.", e);
			return CountDB.UNABLE_TO_COUNT;
		}
	}

	public static final Count countObservedStations()
	{
		try
		{
			return new DatabaseReader().count("SELECT count(*) AS total FROM Station WHERE observe = true;");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count observed stations.", e);
			return CountDB.UNABLE_TO_COUNT;
		}
	}

	public static final Count countLines()
	{
		try
		{
			return new DatabaseReader().count("SELECT count(*) AS total FROM Line;");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count lines.", e);
			return CountDB.UNABLE_TO_COUNT;
		}
	}

	public static final Count countOperators()
	{
		try
		{
			return new DatabaseReader().count("SELECT count(*) AS total FROM Operator;");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count operators.", e);
			return CountDB.UNABLE_TO_COUNT;
		}
	}

	public static final Count countObservedOperators()
	{
		try
		{
			return new DatabaseReader().count(
					"SELECT count(*) AS total FROM (SELECT DISTINCT operator FROM Station WHERE observe=true) t;");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to count observed operators.", e);
			return CountDB.UNABLE_TO_COUNT;
		}
	}
}
