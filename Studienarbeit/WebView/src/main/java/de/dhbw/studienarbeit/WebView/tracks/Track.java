package de.dhbw.studienarbeit.WebView.tracks;

import java.awt.Color;

import de.dhbw.studienarbeit.data.reader.data.station.DelayStationNeighbourData;

public class Track
{
	private final Color color;
	private final DelayStationNeighbourData data;

	public Track(DelayStationNeighbourData data, Color color)
	{
		this.color = color;
		this.data = data;
	}

	public DelayStationNeighbourData getDelayStationNeighbourData()
	{
		return data;
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
