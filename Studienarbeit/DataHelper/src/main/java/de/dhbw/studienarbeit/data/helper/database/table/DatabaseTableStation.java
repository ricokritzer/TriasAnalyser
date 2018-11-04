package de.dhbw.studienarbeit.data.helper.database.table;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.helper.database.DatabaseConverter;
import de.dhbw.studienarbeit.data.helper.database.SqlCondition;
import de.dhbw.studienarbeit.data.helper.database.model.StationDB;

public class DatabaseTableStation extends DatabaseTable
{
	private static final String TABLE_NAME = "Station";

	public DatabaseTableStation() throws IOException
	{
		super();
	}

	public final List<StationDB> selectStations(SqlCondition... conditions) throws IOException
	{
		try
		{
			final List<StationDB> stations = new ArrayList<>();
			select(r -> DatabaseConverter.getStation(r).ifPresent(stations::add), TABLE_NAME, conditions);
			return stations;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting stations does not succeed.", e);
		}
	}

	public final List<StationDB> selectObservedStations() throws IOException
	{
		return selectStations(new SqlCondition("observe", true));
	}
}
