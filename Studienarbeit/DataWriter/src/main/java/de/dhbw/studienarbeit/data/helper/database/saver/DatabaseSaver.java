package de.dhbw.studienarbeit.data.helper.database.saver;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.Settings;
import de.dhbw.studienarbeit.data.helper.database.DatabaseConnector;

public class DatabaseSaver extends DatabaseConnector implements Saver
{
	private static final Logger LOGGER = Logger.getLogger(DatabaseSaver.class.getName());

	private static final DatabaseSaver INSTANCE = new DatabaseSaver();

	private static final String fileName = "errors.txt";

	private static final Saver saver = new TextSaver(fileName);

	public static DatabaseSaver getInstance()
	{
		return INSTANCE;
	}

	@Override
	protected void connectToDatabase() throws SQLException
	{
		connectToDatabase(Settings.getInstance().getDatabaseHostname(), Settings.getInstance().getDatabasePort(),
				Settings.getInstance().getDatabaseName(), Settings.getInstance().getDatabaseWriterUser(),
				Settings.getInstance().getDatabaseWriterPassword());
	}

	@Override
	public void save(Saveable model) throws IOException
	{
		final String sqlQuerry = model.getSQLQuerry();
		if (sqlQuerry.isEmpty())
		{
			LOGGER.log(Level.INFO, "Empty SQLQuerry at " + model.toString());
			return;
		}

		PreparedStatement statement = null;
		try
		{
			reconnectIfNeccessary();

			statement = connection.prepareStatement(model.getSQLQuerry());
			statement.executeUpdate();
			LOGGER.log(Level.FINE, model.toString() + " saved.");
		}
		catch (SQLException | IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to save " + model.toString(), e);
			saver.save(model);
			throw new IOException("Unable to save " + model.toString() + ". Saving SQL in " + fileName);
		}
		finally
		{
			closeStatement(statement);
		}
	}

	@Override
	public void save(Saveable2 model) throws IOException
	{
		reconnectIfNeccessary();

		try (PreparedStatement statement = model.getPreparedStatement(connection))
		{
			statement.executeUpdate();
			LOGGER.log(Level.FINE, model.toString() + " saved.");
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to save " + model.toString(), e);
			saver.save(model);
			throw new IOException("Unable to save " + model.toString() + ". Saving SQL in " + fileName);
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
