package de.dhbw.studienarbeit.data.trias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import de.dhbw.studienarbeit.data.helper.database.saver.Saveable2;

public class Line implements Saveable2
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

	@Override
	public PreparedStatement getPreparedStatement(Connection connection) throws SQLException
	{
		String query = "INSERT INTO Line (name, destination) VALUES (?, ?);";
		try (PreparedStatement statement = connection.prepareStatement(query))
		{
			statement.setString(1, name);
			statement.setString(2, destination);
			return statement;
		}
	}
}
