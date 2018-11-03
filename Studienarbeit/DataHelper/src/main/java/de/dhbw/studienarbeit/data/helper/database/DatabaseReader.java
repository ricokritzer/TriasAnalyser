package de.dhbw.studienarbeit.data.helper.database;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.Settings;
import de.dhbw.studienarbeit.data.helper.database.model.LineDB;
import de.dhbw.studienarbeit.data.helper.database.model.StationDB;
import de.dhbw.studienarbeit.data.helper.database.model.StopDB;
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;

public class DatabaseReader extends DatabaseConnector
{
	private static final String UNABLE_TO_READ = "Unable to read at table ";
	private static final String START_READING_AT_TABLE = "Start reading at table ";
	private static final String ENTRIES_READ = " entries read.";
	private static final String SELECT_FROM = "SELECT * FROM ";
	private static final Logger LOGGER = Logger.getLogger(DatabaseReader.class.getName());

	public DatabaseReader() throws IOException
	{
		super();
	}

	@Override
	protected void connectToDatabase() throws SQLException
	{
		connectToDatabase(Settings.getInstance().getDatabaseReaderUser(),
				Settings.getInstance().getDatabaseReaderPassword());
	}

	@Deprecated
	public List<ApiKey> readApiKeys(final String name) throws SQLException
	{
		return readApiKeys(new SqlCondition("name", name));
	}

	public List<ApiKey> readApiKeys(final SqlCondition... conditions) throws SQLException
	{
		final String tableName = "Api";
		final List<ApiKey> list = new ArrayList<>();
		select(r -> DatabaseConverter.getApiKey(r).ifPresent(list::add), tableName, conditions);
		return list;
	}

	@Deprecated
	public List<LineDB> readLine(final String destination, final String name) throws SQLException
	{
		return readLine(new SqlCondition("name", name), //
				new SqlCondition("destination", destination));
	}

	public List<LineDB> readLine(final SqlCondition... conditions) throws SQLException
	{
		final String tableName = "Line";
		final List<LineDB> list = new ArrayList<>();
		select(r -> DatabaseConverter.getLine(r).ifPresent(list::add), tableName, conditions);
		return list;
	}

	@Deprecated
	public List<StationDB> readStations() throws SQLException
	{
		return readStations(new SqlCondition("observe", true));
	}

	public final List<StationDB> readStations(final SqlCondition... conditions) throws SQLException
	{
		final String tableName = "Station";
		final List<StationDB> stations = new ArrayList<>();
		select(r -> DatabaseConverter.getStation(r).ifPresent(stations::add), tableName, conditions);
		return stations;
	}

	public List<StopDB> readStops(SqlCondition... conditions) throws SQLException
	{
		final String tableName = "Station";
		final List<StopDB> list = new ArrayList<>();
		select(r -> DatabaseConverter.getStop(r).ifPresent(list::add), tableName, conditions);
		return list;
	}

	protected String createSQLStatement(final String tableName, final SqlCondition... condition)
	{
		final StringBuilder sb = new StringBuilder(SELECT_FROM).append(tableName);
		final List<String> conditionStrings = new ArrayList<>();
		Arrays.asList(condition).forEach(c -> conditionStrings.add(c.toString()));

		if (!conditionStrings.isEmpty())
		{
			sb.append(" WHERE ");
		}

		sb.append(String.join(" AND ", conditionStrings));
		sb.append(";");
		return sb.toString();
	}

	private void reconnectIfNeccessary() throws SQLException
	{
		if (connection.isClosed())
		{
			connectToDatabase();
		}
	}

	private void select(Consumer<ResultSet> consumer, String tableName, SqlCondition... conditions) throws SQLException
	{
		LOGGER.log(Level.INFO, START_READING_AT_TABLE + tableName);

		reconnectIfNeccessary();

		final String sql = createSQLStatement(tableName, conditions);
		try (ResultSet result = connection.prepareStatement(sql).executeQuery())
		{
			int counter = 0;
			while (result.next())
			{
				consumer.accept(result);
				counter++;
			}
			LOGGER.log(Level.INFO, counter + ENTRIES_READ);
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, UNABLE_TO_READ + tableName, e);
			throw e;
		}
	}
}
