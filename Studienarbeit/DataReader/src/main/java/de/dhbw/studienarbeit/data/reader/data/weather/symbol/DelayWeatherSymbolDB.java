package de.dhbw.studienarbeit.data.reader.data.weather.symbol;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.weather.text.DelayWeatherTextDB;
import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class DelayWeatherSymbolDB implements DelayWeatherSymbol
{
	private static final Logger LOGGER = Logger.getLogger(DelayWeatherTextDB.class.getName());

	private static final Optional<DelayWeatherSymbolData> getDelay(ResultSet result)
	{
		try
		{
			final DelayMaximum delayMaximum = new DelayMaximum(result.getDouble("delay_max"));
			final DelayAverage delayAverage = new DelayAverage(result.getDouble("delay_avg"));
			final String icon = result.getString("icon");

			return Optional.of(new DelayWeatherSymbolData(delayMaximum, delayAverage, icon));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to " + DelayWeatherTextDB.class.getName(), e);
			return Optional.empty();
		}
	}

	public static String getSQL()
	{
		return "SELECT " + "avg(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_avg, "
				+ "max(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_max, "
				+ "WeatherIcon.icon " + "FROM StopWeather, Stop, Weather, WeatherIcon "
				+ "WHERE Stop.stopID = StopWeather.stopID " + "AND StopWeather.weatherId = Weather.id "
				+ "AND WeatherIcon.text = Weather.text " + "GROUP BY WeatherIcon.icon;";
	}

	public final List<DelayWeatherSymbolData> getDelays() throws IOException
	{
		final String sql = getSQL();

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayWeatherSymbolData> list = new ArrayList<>();
			database.select(r -> getDelay(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
