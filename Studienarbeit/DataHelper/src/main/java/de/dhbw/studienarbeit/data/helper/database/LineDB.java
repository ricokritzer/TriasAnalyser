package de.dhbw.studienarbeit.data.helper.database;

public class LineDB
{
	final int lineID;
	final String name;
	final String destination;

	public LineDB(int lineID, String name, String destination)
	{
		super();
		this.lineID = lineID;
		this.name = name;
		this.destination = destination;
	}

	public int getLineID()
	{
		return lineID;
	}

	public String getName()
	{
		return name;
	}

	public String getDestination()
	{
		return destination;
	}
}
