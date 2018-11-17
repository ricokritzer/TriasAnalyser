package de.dhbw.studienarbeit.data.helper.database.model;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTable;

public class Operator
{
	private static final Logger LOGGER = Logger.getLogger(Operator.class.getName());

	private String name;

	public Operator(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public static final Optional<Operator> getOperator(ResultSet result)
	{
		try
		{
			final String operator = result.getString("operator");
			return Optional.of(new Operator(operator));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to Operator.", e);
			return Optional.empty();
		}
	}

	public static final List<Operator> getAllOperators() throws IOException
	{
		final String sql = "SELECT DISTINCT operator FROM Station;";
		final DatabaseTable database = new DatabaseTable();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<Operator> list = new ArrayList<>();
			database.select(r -> Operator.getOperator(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	public static final List<Operator> getObservedOperators() throws IOException
	{
		final String sql = "SELECT DISTINCT operator FROM Station WHERE observe = true;";
		final DatabaseTable database = new DatabaseTable();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<Operator> list = new ArrayList<>();
			database.select(r -> Operator.getOperator(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
