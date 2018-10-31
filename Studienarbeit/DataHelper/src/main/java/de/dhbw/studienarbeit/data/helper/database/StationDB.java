package de.dhbw.studienarbeit.data.helper.database;

import java.io.IOException;
import java.util.Date;

import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataModel;

public class StationDB implements DataModel
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

	@Override
	public String getSQLQuerry()
	{
		return "INSERT INTO Station " + values() + ";";
	}

	private String values()
	{
		return "values ('" + stationID + "', '" + name + "', " + lat + ", " + lon + ", '" + operator + "')";
	}

	@Override
	public void updateData(ApiKey apiKey) throws IOException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Date nextUpdate()
	{
		// TODO Auto-generated method stub
		return null;
	}
}