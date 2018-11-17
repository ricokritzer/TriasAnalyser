package de.dhbw.studienarbeit.data.helper.database.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OperatorDB
{
	private static final Logger LOGGER = Logger.getLogger(OperatorDB.class.getName());

	private String name;

	public OperatorDB(String name)
	{
		super();
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public static final Optional<OperatorDB> getOperator(ResultSet result)
	{
		try
		{
			final String operator = result.getString("operator");
			return Optional.of(new OperatorDB(operator));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to Operator.", e);
			return Optional.empty();
		}
	}

}
