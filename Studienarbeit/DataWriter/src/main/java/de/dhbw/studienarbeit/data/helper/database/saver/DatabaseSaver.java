package de.dhbw.studienarbeit.data.helper.database.saver;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.Settings;
import de.dhbw.studienarbeit.data.helper.database.DatabaseConnector;
import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;

public class DatabaseSaver extends DatabaseConnector
{
	private static final Logger LOGGER = Logger.getLogger(DatabaseSaver.class.getName());

	private static final DatabaseSaver INSTANCE = new DatabaseSaver();

	private static final String FILE_NAME = "errors.txt";

	private static final TextSaver saver = new TextSaver(FILE_NAME);

	private static final Queue<Saveable> WAITING_FOR_SAVE = new LinkedBlockingQueue<>();

	public DatabaseSaver()
	{
		final Timer timer = new Timer();
		timer.schedule(new MyTimerTask(this::saveFirstModel), new Date(), 100);
	}

	private void saveFirstModel()
	{
		Optional.ofNullable(WAITING_FOR_SAVE.poll()).ifPresent(DatabaseSaver::saveData);
	}

	@Override
	protected void connectToDatabase() throws SQLException
	{
		connectToDatabase(Settings.getInstance().getDatabaseHostname(), Settings.getInstance().getDatabasePort(),
				Settings.getInstance().getDatabaseName(), Settings.getInstance().getDatabaseWriterUser(),
				Settings.getInstance().getDatabaseWriterPassword());
	}

	public static void saveData(Saveable model)
	{
		try
		{
			INSTANCE.saveToDatabase(model);
		}
		catch (IOException e)
		{
			WAITING_FOR_SAVE.add(model);
		}
	}

	private void saveToDatabase(Saveable model) throws IOException
	{
		final String sqlQuerry = model.getSQLQuerry();
		if (sqlQuerry.isEmpty())
		{
			LOGGER.log(Level.FINE, "Empty SQLQuerry at " + model.toString());
			return;
		}

		reconnectIfNeccessary();

		String sql = "Building SQL failed.";

		try (PreparedStatement statement = connection.prepareStatement(sqlQuerry))
		{
			model.setValues(statement);
			sql = statement.toString();
			statement.executeUpdate();
			LOGGER.log(Level.FINE, model.toString() + " saved.");
		}
		catch (SQLException e)
		{
			final String whatHappens = new StringBuilder().append("Unable to save ").append(model)
					.append(". Saving SQL in ").append(FILE_NAME).append(". SQL: ").append(sql).toString();
			LOGGER.log(Level.WARNING, whatHappens, e);
			saver.write(sql);
			throw new IOException(whatHappens, e);
		}
	}

	/*
	 * @deprecated: use static method DataSaver.saveData(Saveable s) instead.
	 */
	@Deprecated
	public void save(Saveable model) throws IOException
	{
		DatabaseSaver.saveData(model);
	}
}
