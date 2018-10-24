package de.dhbw.studienarbeit.data.helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

	public List<Station> readDatabase(final String sqlStatement) throws SQLException
	{
		ResultSet result = null;

		try (PreparedStatement stmt = connection.prepareStatement(sqlStatement))
		{
			if (connection.isClosed())
			{
				connectToDatabase();
			}

			result = stmt.executeQuery();
			final List<Station> stations = new ArrayList<>();
			while (result.next())
			{
				final String stationID = result.getString("stationID");
				final double lat = result.getDouble("lat");
				final double lon = result.getDouble("lon");

				stations.add(new Station(stationID, lat, lon));
				LOGGER.log(Level.INFO, "Read: " + stationID + " at " + lat + "; " + lon);
			}

			return stations;
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to execute SQL statement: " + sqlStatement, e);
			throw e;
		}
		finally
		{
			if (result != null)
			{
				result.close();
			}
		}
	}
}
