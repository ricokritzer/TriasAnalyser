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

	@Deprecated
	public double getLat1()
	{
		return data.getLat1();
	}

	@Deprecated
	public double getLat2()
	{
		return data.getLat2();
	}

	@Deprecated
	public double getLon1()
	{
		return data.getLon1();
	}

	@Deprecated
	public double getLon2()
	{
		return data.getLon2();
	}
}
