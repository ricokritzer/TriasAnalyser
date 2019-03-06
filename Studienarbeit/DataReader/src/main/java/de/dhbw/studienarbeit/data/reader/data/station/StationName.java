package de.dhbw.studienarbeit.data.reader.data.station;

public class StationName
{
	private final String name;

	public StationName(String stationName)
	{
		super();
		this.name = stationName;
	}

	public final String getStationName()
	{
		return name;
	}

	@Override
	public String toString()
	{
		return name;
	}
}
