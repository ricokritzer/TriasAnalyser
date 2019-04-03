package de.dhbw.studienarbeit.data.reader.data.weather.symbol;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class DelayWeatherSymbolDB extends DB<DelayWeatherSymbolData> implements DelayWeatherSymbol
{
	public static String getSQL()
	{
		return "SELECT avg(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_avg, "
				+ "count(*) AS total, "
				+ "max(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_max, "
				+ "icon FROM StopWeather, Stop, Weather, WeatherInformation "
				+ "WHERE Stop.stopID = StopWeather.stopID AND StopWeather.weatherId = Weather.id "
				+ "AND WeatherInformation.id = Weather.weatherInformationID GROUP BY icon ORDER BY delay_avg;";
	}

	public final List<DelayWeatherSymbolData> getDelays() throws IOException
	{
		final String sql = getSQL();
		return readFromDatabase(sql);
	}

	@Override
	protected Optional<DelayWeatherSymbolData> getValue(ResultSet result) throws SQLException
	{
		final DelayMaximum delayMaximum = new DelayMaximum(result.getDouble("delay_max"));
		final DelayAverage delayAverage = new DelayAverage(result.getDouble("delay_avg"));
		final WeatherSymbol symbol = new WeatherSymbol(result.getString("icon"));

		return Optional.of(new DelayWeatherSymbolData(delayMaximum, delayAverage, symbol));
	}
}
