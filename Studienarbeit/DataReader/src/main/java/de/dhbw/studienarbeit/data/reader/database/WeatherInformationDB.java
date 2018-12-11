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

public class WeatherInformationDB
{
	private static final Logger LOGGER = Logger.getLogger(WeatherInformationDB.class.getName());
	private static final String URL_PRE = "http://openweathermap.org/img/w/";
	private static final String URL_END = ".png";

	private final String text;
	private final String textDE;
	private final String icon;

	public WeatherInformationDB(String text, String textDE, String icon)
	{
		super();
		this.text = text;
		this.textDE = textDE;
		this.icon = icon;
	}

	public String getText()
	{
		return text;
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

	private static final Optional<WeatherInformationDB> getDelayLine(ResultSet result)
	{
		try
		{
			final String text = result.getString("text");
			final String textDE = result.getString("textDE");
			final String icon = result.getString("icon");

			return Optional.of(new WeatherInformationDB(text, textDE, icon));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to WeatherInformation.", e);
			return Optional.empty();
		}
	}

	public static final Optional<WeatherInformationDB> getDelayWeatherIcon(String text) throws IOException
	{
		final String sql = "SELECT * FROM WeatherIcon WHERE text = ?;";
		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			preparedStatement.setString(1, text);
			final List<WeatherInformationDB> list = new ArrayList<>();
			database.select(r -> WeatherInformationDB.getDelayLine(r).ifPresent(list::add), preparedStatement);

			if (list.isEmpty())
			{
				return Optional.empty();
			}

			return Optional.of(list.get(0));
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}

	}
}
