package de.dhbw.studienarbeit.data.helper.datamanagement;

public class ApiKey
{
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
}
