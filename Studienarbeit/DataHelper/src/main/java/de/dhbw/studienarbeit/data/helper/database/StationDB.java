package de.dhbw.studienarbeit.data.helper.database;

public class StationDB
{
	private final String stationID;
	private final String name;
	private final double lat;
	private final double lon;
	private final String operator;

	public StationDB(String stationID, String name, double lat, double lon, String operator)
	{
		super();
		this.stationID = stationID;
		this.name = name;
		this.lat = lat;
		this.lon = lon;
		this.operator = operator;
	}

	public String getStationID()
	{
		return stationID;
	}
	
	public String getName()
	{
		return name;
	}

	public double getLat()
	{
		return lat;
	}

	public double getLon()
	{
		return lon;
	}
	
	public String getOperator()
	{
		return operator;
	}
}
