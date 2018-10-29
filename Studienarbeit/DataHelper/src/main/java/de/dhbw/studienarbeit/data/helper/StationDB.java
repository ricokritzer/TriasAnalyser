package de.dhbw.studienarbeit.data.helper;

public class StationDB
{
	private final String stationID;
	private final double lat;
	private final double lon;

	public StationDB(String stationID, double lat, double lon)
	{
		super();
		this.stationID = stationID;
		this.lat = lat;
		this.lon = lon;
	}

	public String getStationID()
	{
		return stationID;
	}

	public double getLat()
	{
		return lat;
	}

	public double getLon()
	{
		return lon;
	}
}
