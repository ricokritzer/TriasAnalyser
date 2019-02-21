package de.dhbw.studienarbeit.data.reader.database;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DelayWeather
{
	private static final Logger LOGGER = Logger.getLogger(DelayWeatherTextDB.class.getName());

	private static String buildSQL(final String what, final String name)
	{
		return new StringBuilder().append("SELECT ")
				.append("avg(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_avg, ")
				.append("max(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_max, ")
				.append(what).append(" AS ").append(name).append(" FROM StopWeather, Stop, Weather, Station ")
				.append("WHERE Stop.stopID = StopWeather.stopID AND Stop.stationID = Station.stationID ")
				.append("AND Weather.lat = ROUND(Station.lat, 2) AND Weather.lon = ROUND(Station.lon, 2) ")
				.append(" AND Weather.timeStamp = StopWeather.timeStamp ")

				.append("GROUP BY ").append(name).append(" ORDER BY delay_avg DESC;").toString();
	}

	private static final Optional<DelayWeatherTextDB> getDelayWeatherText(ResultSet result)
	{
		try
		{
			final double delayMaximum = result.getDouble("delay_max");
			final double delayAverage = result.getDouble("delay_avg");
			final String textDE = result.getString("textDE");
			final String icon = result.getString("icon");

			return Optional.of(new DelayWeatherTextDB(delayAverage, delayMaximum, textDE, icon));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to " + DelayWeatherTextDB.class.getName(), e);
			return Optional.empty();
		}
	}

	public static List<DelayWeatherTextDB> getDelayWeatherText() throws IOException
	{
		final String sql = "SELECT "
				+ "avg(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_avg, "
				+ "max(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_max, "
				+ "WeatherIcon.textDE, " + "WeatherIcon.icon "
				+ "FROM StopWeather, Stop, Weather, Station, WeatherIcon " + "WHERE Stop.stopID = StopWeather.stopID "
				+ "AND Stop.stationID = Station.stationID " + "AND Weather.lat = ROUND(Station.lat, 2) "
				+ "AND Weather.lon = ROUND(Station.lon, 2) " + "AND Weather.timeStamp = StopWeather.timeStamp "
				+ "AND WeatherIcon.text = Weather.text " + "GROUP BY WeatherIcon.textDE, WeatherIcon.icon;";

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayWeatherTextDB> list = new ArrayList<>();
			database.select(r -> getDelayWeatherText(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
