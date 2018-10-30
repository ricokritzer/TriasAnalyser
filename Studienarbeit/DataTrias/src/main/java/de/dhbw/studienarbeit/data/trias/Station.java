package de.dhbw.studienarbeit.data.trias;

import java.io.IOException;
import java.util.Date;

import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataModel;

public class Station implements DataModel
{
	private String stationID;
	private String name;
	private Double lat;
	private Double lon;
	private String operator;

	public Station(String stationID, String name, Double lat, Double lon, String operator)
	{
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

	public Double getLat()
	{
		return lat;
	}

	public Double getLon()
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
	public Date nextUpdate()
	{
		return new Date();
	}

	@Override
	public void updateData(ApiKey apiKey) throws IOException
	{

	}
}
