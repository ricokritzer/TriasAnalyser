package de.dhbw.studienarbeit.data.helper.database.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LineDB
{
	private static final Logger LOGGER = Logger.getLogger(LineDB.class.getName());

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

	public static final Optional<LineDB> getLine(ResultSet result)
	{
		try
		{
			final int lineID = result.getInt("lineID");
			final String lineName = result.getString("name");
			final String lineDestination = result.getString("destination");
			return Optional.of(new LineDB(lineID, lineName, lineDestination));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to line.", e);
			return Optional.empty();
		}
	}
}
