package de.dhbw.studienarbeit.data.reader.data.station;

import java.util.Objects;

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

	public static final double getDistance(Position p1, Position p2)
	{
		final double distanceLat = p1.getLat() - p2.getLat();
		final double distanceLon = p1.getLon() - p2.getLon();
		return Math.sqrt(Math.pow(distanceLat, 2) + Math.pow(distanceLon, 2));
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Position)
		{
			final Position o = (Position) obj;
			return o.getLat() == this.getLat() && o.getLon() == this.getLon();
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(lat, lon);
	}
}
