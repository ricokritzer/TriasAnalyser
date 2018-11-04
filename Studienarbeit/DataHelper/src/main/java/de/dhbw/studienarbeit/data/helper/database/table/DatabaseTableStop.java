package de.dhbw.studienarbeit.data.helper.database.table;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.helper.database.DatabaseConverter;
import de.dhbw.studienarbeit.data.helper.database.SqlCondition;
import de.dhbw.studienarbeit.data.helper.database.model.StopDB;

public class DatabaseTableStop extends DatabaseTable
{
	private static final String TABLE_NAME = "Stop";

	public DatabaseTableStop() throws IOException
	{
		super();
	}

	public final List<StopDB> selectStops(SqlCondition... conditions) throws IOException
	{
		try
		{
			final List<StopDB> list = new ArrayList<>();
			select(r -> DatabaseConverter.getStop(r).ifPresent(list::add), TABLE_NAME, conditions);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting stations does not succeed.", e);
		}
	}
}
