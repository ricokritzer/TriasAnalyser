package de.dhbw.studienarbeit.data.helper.database.table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.model.WeatherDB;

public class DatabaseTableWeather extends DatabaseTable
{
	private static final Logger LOGGER = Logger.getLogger(DatabaseTableWeather.class.getName());
	private static final String TABLE_NAME = "Weather";

	private final Optional<WeatherDB> getLine(ResultSet result)
	{
		try
		{
			final int lat = result.getInt("lat");
			final int lon = result.getInt("lon");
			final Date timeStamp = result.getDate("timeStamp");
			final double temp = result.getDouble("temp");
			final double humidity = result.getDouble("humidity");
			final double pressure = result.getDouble("pressure");
			final double wind = result.getDouble("wind");
			final double clouds = result.getDouble("clouds");
			final String text = result.getString("text");

			return Optional.of(new WeatherDB(lat, lon, timeStamp, temp, humidity, pressure, wind, clouds, text));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to line.", e);
			return Optional.empty();
		}
	}

	@Override
	protected String getTableName()
	{
		return TABLE_NAME;
	}
}
