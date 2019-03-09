package de.dhbw.studienarbeit.data.reader.data.weather;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class DelayWeatherCorrelationDB
{
	private static final Logger LOGGER = Logger.getLogger(DelayWeatherCorrelationDB.class.getName());

	private static final Optional<CorrelationData> getDelay(ResultSet result, String fieldname)
	{
		try
		{
			final double delay = result.getDouble("delay");
			final double value = result.getDouble(fieldname);

			return Optional.of(new CorrelationData(delay, value));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to " + DelayWeatherCorrelationDB.class.getName(), e);
			return Optional.empty();
		}
	}

	public static final List<CorrelationData> getDelay(String fieldname) throws IOException
	{
		final String sql = getSqlFor(fieldname);

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<CorrelationData> list = new ArrayList<>();
			database.select(r -> getDelay(r, fieldname).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	public static final String getSqlFor(String what)
	{
		return new StringBuilder("SELECT ").append(what)
				.append(", (UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay ")
				.append("FROM StopWeather, Stop, Weather ")
				.append("WHERE Stop.stopID = StopWeather.stopID AND StopWeather.weatherId = Weather.id;").toString();
	}
}
