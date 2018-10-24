package de.dhbw.studienarbeit.data.helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseReader extends DatabaseConnector
{
	private static final Logger LOGGER = Logger.getLogger(TextSaver.class.getName());

	public DatabaseReader() throws SQLException, ReflectiveOperationException
	{
		super();
		connectToDatabase();
	}

	private void connectToDatabase() throws SQLException
	{
		connectToDatabase(ConfigurationData.DATABASE_USER_READER, ConfigurationData.DATABASE_PASSWORD_READER);
	}

	public ResultSet readDatabase(final String sqlStatement) throws SQLException
	{
		try (PreparedStatement stmt = connection.prepareStatement(sqlStatement))
		{
			if (connection.isClosed())
			{
				connectToDatabase();
			}

			ResultSet set = stmt.executeQuery();
			LOGGER.log(Level.INFO, "Executed SQL statment: " + sqlStatement);
			return set;
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to execute SQL statement: " + sqlStatement, e);
			throw e;
		}
	}
}
