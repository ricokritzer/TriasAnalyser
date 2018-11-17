package de.dhbw.studienarbeit.data.helper.database.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.model.Operator;

public class OperatorDB extends Operator
{
	public OperatorDB(String name)
	{
		super(name);
	}

	private static final Logger LOGGER = Logger.getLogger(OperatorDB.class.getName());

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
