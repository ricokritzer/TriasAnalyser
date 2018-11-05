package de.dhbw.studienarbeit.data.trias;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import de.dhbw.studienarbeit.data.helper.database.saver.DataSaverModel;
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataModel;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataModel2;

public class Line implements DataSaverModel
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
