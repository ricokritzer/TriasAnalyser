package de.dhbw.studienarbeit.data.reader.data.api;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKeyData;
import de.dhbw.studienarbeit.data.reader.data.operator.OperatorID;
import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class ApiKeyDB
{
	private static final Logger LOGGER = Logger.getLogger(ApiKeyDB.class.getName());

	private static final Optional<ApiKeyData> getApiKey(ResultSet result)
	{
		try
		{
			final String key = result.getString("apiKey");
			final int requests = result.getInt("maximumRequests");
			final String url = result.getString("url");
			return Optional.of(new ApiKeyData(key, requests, url));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to api key.", e);
			return Optional.empty();
		}
	}

	public static final List<ApiKeyData> getApiKeys(OperatorID operator) throws IOException
	{
		final String sql = "SELECT * FROM Api WHERE name = ?;";
		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			preparedStatement.setString(1, operator.getName());

			final List<ApiKeyData> list = new ArrayList<>();
			database.select(r -> getApiKey(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Unable to select apis by name " + operator.getName(), e);
		}
	}
}
