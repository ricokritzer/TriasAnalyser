package de.dhbw.studienarbeit.data.reader.data.weather.clouds;

public class Clouds
{
	protected static final String DATABASE_FIELDNAME = "clouds";
	protected static final String DATABASE_REQUEST = "round(clouds, 0) AS rounded";

	private final int value;

	public Clouds(int clouds)
	{
		super();
		this.value = clouds;
	}

	public int getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		return new StringBuilder().append(value).append("%").toString();
	}

}
