package de.dhbw.studienarbeit.data.trias;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import de.dhbw.studienarbeit.data.helper.database.saver.Saveable;

public class Line implements Saveable
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
	public String getSQLQuerry()
	{
		return "INSERT INTO Line (name, destination) SELECT * FROM (SELECT ? n, ? d) AS tmp WHERE NOT EXISTS (SELECT name FROM Line WHERE name = n AND destination = d);";
	}

	@Override
	public void setValues(PreparedStatement preparedStatement) throws SQLException
	{
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, destination);
	}
}
