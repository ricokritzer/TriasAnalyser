package de.dhbw.studienarbeit.data.reader.data.weather;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.database.DB;

public abstract class DelayWeatherDB<T> extends DB<DelayData<T>> implements Delay<T>
{
	public final List<DelayData<T>> getDelays() throws IOException
	{
		return readFromDatabase(getSQL());
	}

	@Override
	protected Optional<DelayData<T>> getValue(ResultSet result) throws SQLException
	{
		final DelayMaximum delayMaximum = new DelayMaximum(result.getDouble("delay_max"));
		final DelayAverage delayAverage = new DelayAverage(result.getDouble("delay_avg"));
		final CountData count = new CountData(result.getInt("total"));
		final T element = getElement(result);

		return Optional.of(new DelayData<T>(delayMaximum, delayAverage, count, element));
	}

	public static String buildSQL(final String what, final String name)
	{
		return new StringBuilder().append("SELECT ").append("count(*) AS total, ")
				.append("avg(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_avg, ")
				.append("max(UNIX_TIMESTAMP(Stop.realTime) - UNIX_TIMESTAMP(Stop.timeTabledTime)) AS delay_max, ")
				.append(what).append(" AS ").append(name).append(" FROM StopWeather, Stop, Weather ")
				.append("WHERE Stop.stopID = StopWeather.stopID AND StopWeather.weatherId = Weather.id ")
				.append("AND realTime IS NOT NULL ").append("GROUP BY ").append(name).append(" ORDER BY ").append(name)
				.append(";").toString();
	}

	protected abstract T getElement(ResultSet result) throws SQLException;

	protected abstract String getSQL();
}
