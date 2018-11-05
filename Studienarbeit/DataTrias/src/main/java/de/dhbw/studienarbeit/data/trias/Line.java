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
	
	public Line(int id, String name, String destination)
	{
		this.id = id;
		this.name = name;
		this.destination = destination;
	}
	
	public Line(String publishedLineName, String destinationText)
	{
		this.id = 0;
		this.name = publishedLineName;
		this.destination = destinationText;
	}

	@Override
	public String getSQLQuerry()
	{
		return "INSERT INTO Line (name, destination) VALUES ('" + name + "', '" + destination + "')";
	}

	@Override
	public void updateData(ApiKey apiKey) throws IOException
	{
		// lines will not be updated
	}

	@Override
	public Date nextUpdate()
	{
		// lines will not be updated
		return new Date();
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
	
	/**
	 * equals evaluates to true, if the id of two lines is the same
	 */
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
