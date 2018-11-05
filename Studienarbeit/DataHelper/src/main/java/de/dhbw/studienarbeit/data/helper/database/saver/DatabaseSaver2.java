package de.dhbw.studienarbeit.data.helper.database.saver;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.Settings;
import de.dhbw.studienarbeit.data.helper.database.DatabaseConnector;

public class DatabaseSaver2 extends DatabaseConnector
{
	private static final Logger LOGGER = Logger.getLogger(DatabaseSaver2.class.getName());
	private static final DatabaseSaver2 SAVER = new DatabaseSaver2();
	private static final TextSaver saverForErrors = new TextSaver("errors.txt");

	private DatabaseSaver2()
	{}

	@Override
	protected void connectToDatabase() throws SQLException
	{
		connectToDatabase(Settings.getInstance().getDatabaseWriterUser(),
				Settings.getInstance().getDatabaseWriterPassword());
	}

	public static void save(DataSaverModel model)
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
			SAVER.reconnectIfNeccessary();

			statement = SAVER.connection.prepareStatement(model.getSQLQuerry());
			statement.executeUpdate();
			LOGGER.log(Level.FINE, model.toString() + " saved.");
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to save " + model.toString(), e);
			saverForErrors.save(model);
		}
		finally
		{
			SAVER.closeStatement(statement);
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
