package de.dhbw.studienarbeit.data.reader.data.weather.text;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.weather.Delays;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class DelayWeatherTextDB extends DB<DelayData<WeatherText>> implements Delays<WeatherText>
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
	protected Optional<DelayData<WeatherText>> getValue(ResultSet result) throws SQLException
	{
		final DelayMaximum delayMaximum = new DelayMaximum(result.getDouble("delay_max"));
		final DelayAverage delayAverage = new DelayAverage(result.getDouble("delay_avg"));
		final CountData count = new CountData(result.getInt("total"));
		final WeatherText textDE = new WeatherText(result.getString("textDE"));

		return Optional.of(new DelayData<WeatherText>(delayMaximum, delayAverage, count, textDE));
	}

}
