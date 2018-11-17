package de.dhbw.studienarbeit.data.helper.datamanagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApiKey
{
	private static final Logger LOGGER = Logger.getLogger(ApiKey.class.getName());

	private final String key;
	private final int requestsPerMinute;
	private final String url;

	public ApiKey(String key, int requestsPerMinute, String url)
	{
		super();
		this.key = key;
		this.requestsPerMinute = requestsPerMinute;
		this.url = url;
	}

	public String getKey()
	{
		return key;
	}

	public int getRequestsPerMinute()
	{
		return requestsPerMinute;
	}

	public String getUrl()
	{
		return url;
	}

	public int delayBetweenRequests()
	{
		return (60 * 1000) / requestsPerMinute;
	}

	public static final Optional<ApiKey> getApiKey(ResultSet result)
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
}
