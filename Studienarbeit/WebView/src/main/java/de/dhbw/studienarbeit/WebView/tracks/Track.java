package de.dhbw.studienarbeit.WebView.tracks;

import java.awt.Color;

import de.dhbw.studienarbeit.data.reader.database.StationNeighbourDB;

public class Track extends StationNeighbourDB
{
	private final Color color;

	public Track(double lat1, double lon1, double avg1, double lat2, double lon2, double avg2, Color color)
	{
		super(lat1, lon1, avg1, lat2, lon2, avg2);
		this.color = color;
	}

	public Color getColor()
	{
		return color;
	}
}
