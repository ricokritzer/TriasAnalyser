package de.dhbw.studienarbeit.data.helper.database.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;

public class ApiKeyDB extends ApiKey
{
	public ApiKeyDB(String key, int requestsPerMinute, String url)
	{
		super(key, requestsPerMinute, url);
	}

	private static final Logger LOGGER = Logger.getLogger(ApiKeyDB.class.getName());

	public static final Optional<ApiKeyDB> getApiKey(ResultSet result)
	{
		try
		{
			final String key = result.getString("apiKey");
			final int requests = result.getInt("maximumRequests");
			final String url = result.getString("url");
			return Optional.of(new ApiKeyDB(key, requests, url));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to api key.", e);
			return Optional.empty();
		}
	}
}
