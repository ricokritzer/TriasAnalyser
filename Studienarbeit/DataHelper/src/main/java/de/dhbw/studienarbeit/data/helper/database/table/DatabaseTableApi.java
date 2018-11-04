package de.dhbw.studienarbeit.data.helper.database.table;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.SqlCondition;
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;

public class DatabaseTableApi extends DatabaseTable
{
	private static final Logger LOGGER = Logger.getLogger(DatabaseTableApi.class.getName());
	private static final String TABLE_NAME = "Api";

	public DatabaseTableApi() throws IOException
	{
		super();
	}

	private final Optional<ApiKey> getApiKey(ResultSet result)
	{
		try
		{
			final String key = result.getString("apiKey");
			final int requests = result.getInt("maximumRequests");
			final String url = result.getString("url");
			return Optional.of(new ApiKey(key, requests, url));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to api key.", e);
			return Optional.empty();
		}
	}

	public final List<ApiKey> selectApis(SqlCondition... conditions) throws IOException
	{
		try
		{
			final List<ApiKey> list = new ArrayList<>();
			select(r -> getApiKey(r).ifPresent(list::add), TABLE_NAME, conditions);
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
