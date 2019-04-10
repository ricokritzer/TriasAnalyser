package de.dhbw.studienarbeit.data.reader.data.weather.text;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherDB;

public class DelayWeatherTextDB extends DelayWeatherDB<WeatherText>
{
	@Override
	protected WeatherText getElement(ResultSet result) throws SQLException
	{
		return new WeatherText(result.getString("textDE"));
	}

	@Override
	protected String getSQL()
	{
		return "SELECT count(*) AS total, avg(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_avg, "
				+ "max(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_max, "
				+ "textDE FROM StopWeather, Stop, Weather, WeatherInformation "
				+ "WHERE Stop.stopID = StopWeather.stopID AND StopWeather.weatherId = Weather.id "
				+ "AND WeatherInformation.id = Weather.weatherInformationID GROUP BY textDE";
	}
}
