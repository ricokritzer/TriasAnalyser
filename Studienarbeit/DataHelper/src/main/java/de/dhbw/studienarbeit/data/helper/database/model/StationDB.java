package de.dhbw.studienarbeit.data.helper.database.model;

import de.dhbw.studienarbeit.data.helper.database.saver.DataSaverModel;

public class StationDB implements DataSaverModel
{
	private final String stationID;
	private final String name;
	private final double lat;
	private final double lon;
	private final String operator;
	private final boolean observe;

	public StationDB(String stationID, String name, double lat, double lon, String operator, boolean observe)
	{
		super();
		this.stationID = stationID;
		this.name = name;
		this.lat = lat;
		this.lon = lon;
		this.operator = operator;
		this.observe = observe;
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
	
	public boolean isObserve()
	{
		return observe;
	}

	@Override
	public String getSQLQuerry()
	{
		return "INSERT INTO Station " + values() + ";";
	}

	private String values()
	{
		return "values ('" + stationID + "', '" + name + "', " + lat + ", " + lon + ", '" + operator + "', " + observe + ")";
	}
}
