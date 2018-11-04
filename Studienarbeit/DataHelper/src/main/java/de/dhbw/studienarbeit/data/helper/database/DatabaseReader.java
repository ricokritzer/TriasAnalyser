package de.dhbw.studienarbeit.data.helper.database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import de.dhbw.studienarbeit.data.helper.Settings;
import de.dhbw.studienarbeit.data.helper.database.model.LineDB;
import de.dhbw.studienarbeit.data.helper.database.model.StationDB;
import de.dhbw.studienarbeit.data.helper.database.model.StopDB;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableApi;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableLine;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableStation;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableStop;
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;

public class DatabaseReader extends DatabaseConnector
{
	/**
	 * @deprecated use the special table instead.
	 */
	@Deprecated
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

	@Deprecated
	public List<ApiKey> readApiKeys(final SqlCondition... conditions) throws SQLException
	{
		try
		{
			return new DatabaseTableApi().selectApis(conditions);
		}
		catch (IOException e)
		{
			throw throwException(e);
		}
	}

	@Deprecated
	public List<LineDB> readLine(final String destination, final String name) throws SQLException
	{
		return readLine(new SqlCondition("name", name), //
				new SqlCondition("destination", destination));
	}

	@Deprecated
	public List<LineDB> readLine(final SqlCondition... conditions) throws SQLException
	{
		try
		{
			return new DatabaseTableLine().selectLines(conditions);
		}
		catch (IOException e)
		{
			throw throwException(e);
		}
	}

	@Deprecated
	public List<StationDB> readStations() throws SQLException
	{
		return readStations(new SqlCondition("observe", true));
	}

	@Deprecated
	public final List<StationDB> readStations(final SqlCondition... conditions) throws SQLException
	{
		try
		{
			return new DatabaseTableStation().selectStations(conditions);
		}
		catch (IOException e)
		{
			throw throwException(e);
		}
	}

	@Deprecated
	public List<StopDB> readStops(SqlCondition... conditions) throws SQLException
	{
		try
		{
			return new DatabaseTableStop().selectStops(conditions);
		}
		catch (IOException e)
		{
			throw throwException(e);
		}
	}

	private SQLException throwException(IOException e)
	{
		return new SQLException("Error in deprecated function.", e);
	}
}
