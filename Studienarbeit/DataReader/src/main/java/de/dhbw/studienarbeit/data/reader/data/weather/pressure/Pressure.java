package de.dhbw.studienarbeit.data.reader.data.weather.pressure;

public class Pressure
{
	private final int value;

	public Pressure(int value)
	{
		super();
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		return new StringBuilder().append(value).append("hPa").toString();
	}
}
