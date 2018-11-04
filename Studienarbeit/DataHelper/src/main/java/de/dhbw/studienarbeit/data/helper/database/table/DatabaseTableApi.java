package de.dhbw.studienarbeit.data.helper.database.table;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.helper.database.DatabaseConverter;
import de.dhbw.studienarbeit.data.helper.database.SqlCondition;
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;

public class DatabaseTableApi extends DatabaseTable
{
	private static final String TABLE_NAME = "Api";

	public DatabaseTableApi() throws IOException
	{
		super();
	}

	public final List<ApiKey> selectApis(SqlCondition... conditions) throws IOException
	{
		try
		{
			final List<ApiKey> list = new ArrayList<>();
			select(r -> DatabaseConverter.getApiKey(r).ifPresent(list::add), TABLE_NAME, conditions);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting stations does not succeed.", e);
		}
	}

	public final List<ApiKey> selectApisByName(final String name) throws IOException
	{
		return selectApis(new SqlCondition("name", name));
	}
}
