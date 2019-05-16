package de.dhbw.studienarbeit.data.reader.data.weather;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.database.DB;

public abstract class CancelledStopsDB<T> extends DB<CancelledStopsData<T>> implements CancelledStops<T>
{
	public static String buildSQLCountCancelledStops(final String what, final String name)
	{
		return new StringBuilder().append("SELECT ").append("count(*) AS total, ").append(what).append(" AS ")
				.append(name).append(" FROM StopWeather, Stop, Weather ")
				.append("WHERE Stop.stopID = StopWeather.stopID AND StopWeather.weatherId = Weather.id ")
				.append("AND realTime IS NULL ").append("GROUP BY ").append(name).append(" ORDER BY ").append(name)
				.append(";").toString();
	}

	@Override
	public List<CancelledStopsData<T>> getCancelledStops() throws IOException
	{
		return readFromDatabase(getSQL());
	}

	@Override
	protected Optional<CancelledStopsData<T>> getValue(ResultSet result) throws SQLException
	{
		final CountData count = new CountData(result.getLong("total"));
		final T element = getElement(result);

		// TODO: count total and cancelled stops
		return Optional.of(new CancelledStopsData<T>(element, count, count));
	}

	protected abstract String getSQL();

	protected abstract T getElement(ResultSet result) throws SQLException;
}
