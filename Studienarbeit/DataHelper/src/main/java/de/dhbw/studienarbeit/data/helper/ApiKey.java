package de.dhbw.studienarbeit.data.helper;

public class ApiKey
{
	private final String key;
	private final int requestsPerMinute;

	public ApiKey(String key, int requestsPerMinute)
	{
		super();
		this.key = key;
		this.requestsPerMinute = requestsPerMinute;
	}

	public String getKey()
	{
		return key;
	}

	public int getRequestsPerMinute()
	{
		return requestsPerMinute;
	}

	public int delayBetweenRequests()
	{
		return (60 * 1000) / requestsPerMinute;
	}
}
