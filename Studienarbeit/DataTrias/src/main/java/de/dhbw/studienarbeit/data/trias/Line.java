package de.dhbw.studienarbeit.data.trias;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataModel;

public class Line implements DataModel
{
	private int id;
	private String name; 
	private String destination;
	
	public Line(String name, String destination)
	{
		this.name = name;
		this.destination = destination;
	}
	
	@Override
	public String getSQLQuerry()
	{
		// TODO Auto-generated method stub
		return null;
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

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public String getDestination()
	{
		return destination;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Line && ((Line) obj).getId() == id;
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(id);
	}
	
}
