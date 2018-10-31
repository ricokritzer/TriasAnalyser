package de.dhbw.studienarbeit.data.helper.database;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.Settings;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataModel;

public class DatabaseSaver extends DatabaseConnector implements Saver
{
	private static final Logger LOGGER = Logger.getLogger(TextSaver.class.getName());

	public DatabaseSaver() throws IOException
	{
		super();
	}

	@Override
	protected void connectToDatabase() throws SQLException
	{
		connectToDatabase(Settings.getInstance().getDatabaseWriterUser(),
				Settings.getInstance().getDatabaseWriterPassword());
	}

	@Override
	public void save(DataModel model)
	{
		PreparedStatement statement = null;
		try
		{
			if (connection.isClosed())
			{
				connectToDatabase();
			}

			statement = connection.prepareStatement(model.getSQLQuerry());
			statement.executeUpdate();
			LOGGER.log(Level.INFO, "Saving datamodel: " + model.toString());
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to save " + model.toString(), e);
			saver.save(model);
		}
		finally
		{
			closeStatement(statement);
		}
	}

	private void closeStatement(PreparedStatement statement)
	{
		Optional.ofNullable(statement).ifPresent(e -> {
			try
			{
				e.close();
			}
			catch (SQLException e1)
			{
				LOGGER.log(Level.WARNING, "Unable to close PreparedStatement.", e1);
			}
		});
	}
}