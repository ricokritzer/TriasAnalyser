package de.dhbw.studienarbeit.data.reader.data.weather.text;

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
import de.dhbw.studienarbeit.data.reader.data.weather.symbol.WeatherSymbol;
import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class DelayWeatherTextDB implements DelayWeatherText
{
	private static final Logger LOGGER = Logger.getLogger(DelayWeatherTextDB.class.getName());

	private static final Optional<DelayWeatherTextData> getDelayLine(ResultSet result)
	{
		try
		{
			final DelayMaximum delayMaximum = new DelayMaximum(result.getDouble("delay_max"));
			final DelayAverage delayAverage = new DelayAverage(result.getDouble("delay_avg"));
			final String textDE = result.getString("textDE");
			final WeatherSymbol symbol = new WeatherSymbol(result.getString("icon"));

			return Optional.of(new DelayWeatherTextData(delayMaximum, delayAverage, textDE, symbol));
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
				+ "WeatherIcon.textDE, " + "WeatherIcon.icon " + "FROM StopWeather, Stop, Weather, WeatherIcon "
				+ "WHERE Stop.stopID = StopWeather.stopID " + "AND StopWeather.weatherId = Weather.id "
				+ "AND WeatherIcon.text = Weather.text " + "GROUP BY WeatherIcon.textDE, WeatherIcon.icon;";
	}

	public final List<DelayWeatherTextData> getDelays() throws IOException
	{
		final String sql = getSQL();

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayWeatherTextData> list = new ArrayList<>();
			database.select(r -> DelayWeatherTextDB.getDelayLine(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
