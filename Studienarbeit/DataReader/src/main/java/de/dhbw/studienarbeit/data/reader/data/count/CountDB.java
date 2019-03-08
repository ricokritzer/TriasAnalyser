package de.dhbw.studienarbeit.data.reader.data.count;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CountDB
{
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
		return new CountStopsDB().countStops();
	}

	public static final Count countWeather()
	{
		return new CountWeatherDB().countWeather();
	}

	public static final Count countStations()
	{
		return new CountStationsDB().countStations();
	}

	public static final Count countStationsWithRealtimeData()
	{
		return new CountStationsDB().countStationsWithRealtimeData();
	}

	public static final Count countObservedStations()
	{
		return new CountStationsDB().countObservedStations();
	}

	public static final Count countLines()
	{
		return new CountLinesDB().countLines();
	}

	public static final Count countOperators()
	{
		return new CountOperatorsDB().countOperators();
	}

	public static final Count countObservedOperators()
	{
		return new CountOperatorsDB().countObservedOperators();
	}
}
