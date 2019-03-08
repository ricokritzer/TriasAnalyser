package de.dhbw.studienarbeit.data.reader.data.count;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CountDB
{
	private CountDB()
	{}

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

	@Deprecated
	public static final Count countStops()
	{
		return new CountStopsDB().countStops();
	}

	@Deprecated
	public static final Count countWeather()
	{
		return new CountWeatherDB().countWeather();
	}

	@Deprecated
	public static final Count countStations()
	{
		return new CountStationsDB().countStations();
	}

	@Deprecated
	public static final Count countStationsWithRealtimeData()
	{
		return new CountStationsDB().countStationsWithRealtimeData();
	}

	@Deprecated
	public static final Count countObservedStations()
	{
		return new CountStationsDB().countObservedStations();
	}

	@Deprecated
	public static final Count countLines()
	{
		return new CountLinesDB().countLines();
	}

	@Deprecated
	public static final Count countOperators()
	{
		return new CountOperatorsDB().countOperators();
	}

	@Deprecated
	public static final Count countObservedOperators()
	{
		return new CountOperatorsDB().countObservedOperators();
	}
}
