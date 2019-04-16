package de.dhbw.studienarbeit.data.reader.data.weather.symbol;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.reader.data.DelayDB;

public class DelayWeatherSymbolDB extends DelayDB<WeatherSymbol>
{
	@Override
	protected WeatherSymbol getElement(ResultSet result) throws SQLException
	{
		return new WeatherSymbol(result.getString("icon"));
	}

	@Override
	protected String getSQL()
	{
		return "SELECT avg(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_avg, "
				+ "count(*) AS total, "
				+ "max(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_max, "
				+ "icon FROM StopWeather, Stop, Weather, WeatherInformation "
				+ "WHERE Stop.stopID = StopWeather.stopID AND StopWeather.weatherId = Weather.id "
				+ "AND WeatherInformation.id = Weather.weatherInformationID GROUP BY icon ORDER BY delay_avg;";
	}
}
