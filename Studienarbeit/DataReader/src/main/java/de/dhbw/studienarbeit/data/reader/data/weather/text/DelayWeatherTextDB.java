package de.dhbw.studienarbeit.data.reader.data.weather.text;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayDB;

public class DelayWeatherTextDB extends DelayDB<WeatherText>
{
	public static String getSQL()
	{
		return "SELECT count(*) AS total, avg(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_avg, "
				+ "max(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_max, "
				+ "textDE FROM StopWeather, Stop, Weather, WeatherInformation "
				+ "WHERE Stop.stopID = StopWeather.stopID AND StopWeather.weatherId = Weather.id "
				+ "AND WeatherInformation.id = Weather.weatherInformationID GROUP BY textDE";
	}

	@Override
	public List<DelayData<WeatherText>> getDelays() throws IOException
	{
		final String sql = getSQL();
		return readFromDatabase(sql);
	}

	@Override
	protected WeatherText getElement(ResultSet result) throws SQLException
	{
		return new WeatherText(result.getString("textDE"));
	}
}
