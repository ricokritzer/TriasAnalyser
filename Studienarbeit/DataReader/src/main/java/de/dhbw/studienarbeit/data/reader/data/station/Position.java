package de.dhbw.studienarbeit.data.reader.data.station;

public class Position
{
	private final double lat;
	private final double lon;

	public Position(double lat, double lon)
	{
		super();
		this.lat = lat;
		this.lon = lon;
	}

	public final double getLat()
	{
		return lat;
	}

	public final double getLon()
	{
		return lon;
	}
}
