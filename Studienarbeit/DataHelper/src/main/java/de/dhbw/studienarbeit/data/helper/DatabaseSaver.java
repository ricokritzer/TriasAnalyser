package de.dhbw.studienarbeit.data.helper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseSaver extends DatabaseConnector implements Saver
{
	private static final Logger LOGGER = Logger.getLogger(TextSaver.class.getName());

	public DatabaseSaver() throws SQLException, ReflectiveOperationException
	{
		super();
		connectToDatabase();
	}

	private void connectToDatabase() throws SQLException
	{
		connectToDatabase(ConfigurationData.DATABASE_USER_WRITER, ConfigurationData.DATABASE_PASSWORD_WRITER);
	}

	@Override
	public void save(DataModel model)
	{
		try (PreparedStatement stmt = connection.prepareStatement(model.getSQLQuerry()))
		{
			if (connection.isClosed())
			{
				connectToDatabase();
			}

			stmt.executeUpdate();
			LOGGER.log(Level.INFO, "Saving datamodel: " + model.toString());
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to save " + model.toString(), e);
			saver.save(model);
		}
	}
}
