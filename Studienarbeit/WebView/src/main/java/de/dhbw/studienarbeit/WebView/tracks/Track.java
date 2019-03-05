package de.dhbw.studienarbeit.WebView.tracks;

import java.awt.Color;

import de.dhbw.studienarbeit.data.reader.database.DelayStationNeighbourDB;

public class Track extends DelayStationNeighbourDB
{
	private final Color color;

	public Track(double lat1, double lon1, double avg1, double lat2, double lon2, double avg2, Color color)
	{
		super("foo", lat1, lon1, avg1, "bar", lat2, lon2, avg2);
		this.color = color;
	}

	public Color getColor()
	{
		return color;
	}

	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj);
	}

	@Override
	public int hashCode()
	{
		return super.hashCode();
	}
}
