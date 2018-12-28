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

public class DelayWeatherTextDB
{
	private static final Logger LOGGER = Logger.getLogger(DelayWeatherTextDB.class.getName());
	private static final String URL_PRE = "http://openweathermap.org/img/w/";
	private static final String URL_END = ".png";

	private final double average;
	private final double maximum;
	private final String textDE;
	private final String icon;

	public DelayWeatherTextDB(double average, double maximum, String textDE, String icon)
	{
		super();
		this.average = average;
		this.maximum = maximum;
		this.textDE = textDE;
		this.icon = icon;
	}

	public double getAverage()
	{
		return average;
	}

	public double getMaximum()
	{
		return maximum;
	}

	public String getTextDE()
	{
		return textDE;
	}

	public String getIcon()
	{
		return icon;
	}

	public String getIconURL()
	{
		return new StringBuilder(URL_PRE).append(icon).append(URL_END).toString();
	}

	private static final Optional<DelayWeatherTextDB> getDelayLine(ResultSet result)
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

	public static String getSQL()
	{
		return new StringBuilder().append("SELECT ")
				.append("avg(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_avg, ")
				.append("max(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_max, ")
				.append("WeatherIcon.textDE, ") //
				.append("WeatherIcon.icon") //
				.append(" FROM Stop, Weather, Station, WeatherIcon ") //
				.append("WHERE ") //
				.append("ROUND(Station.lat, 2) = Weather.lat AND ROUND(Station.lon, 2) = Weather.lon AND ") //
				.append("Stop.realTime < DATE_ADD(Weather.timeStamp,INTERVAL 10 MINUTE) AND ") //
				.append("Stop.realTime > DATE_SUB(Weather.timeStamp,INTERVAL 10 MINUTE) AND ") //
				.append("Station.stationID = Stop.stationID AND ") //
				.append("WeatherIcon.text = Weather.text ") //
				.append("GROUP BY WeatherIcon.textDE, WeatherIcon.icon;").toString();
	}

	public static final List<DelayWeatherTextDB> getDelays() throws IOException
	{
		final String sql = getSQL();

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayWeatherTextDB> list = new ArrayList<>();
			database.select(r -> DelayWeatherTextDB.getDelayLine(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
