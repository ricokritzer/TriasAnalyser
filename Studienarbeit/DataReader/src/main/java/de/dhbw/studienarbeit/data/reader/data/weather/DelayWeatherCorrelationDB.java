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

	private static final Optional<Double> getDelay(ResultSet result, String fieldname)
	{
		try
		{
			final double value = result.getDouble("correlation");

			return Optional.of(Double.valueOf(value));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to " + DelayWeatherCorrelationDB.class.getName(), e);
			return Optional.empty();
		}
	}

	public static final double getCorrelationData(String fieldname) throws IOException
	{
		final String sql = getSqlFor(fieldname);

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<Double> list = new ArrayList<>();
			database.select(r -> getDelay(r, fieldname).ifPresent(list::add), preparedStatement);

			return list.get(0).doubleValue();
		}
		catch (SQLException | IndexOutOfBoundsException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	public static final String getSqlFor(String fieldname)
	{
		return new StringBuilder() //
				.append("SELECT sum((x-avgX) * (y-avgY)) / SQRT(sum(POW(x-avgX,2))*sum(POW(y-avgY,2))) AS correlation ")
				.append("FROM ") //
				.append("(SELECT Weather.% AS x, UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime) AS y FROM StopWeather, Stop, Weather WHERE StopWeather.stopID = Stop.stopID AND StopWeather.weatherID = Weather.id) t, ")
				.append("(SELECT avg(%) AS avgX FROM StopWeather, Weather WHERE StopWeather.weatherID = Weather.id) tX, ") //
				.append("(SELECT avg(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS avgY FROM StopWeather, Stop WHERE StopWeather.stopID = Stop.stopID) tY;")
				.toString().replaceAll("%", fieldname);
	}
}
