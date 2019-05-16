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
		return "SELECT total, cancelled, t.v AS " + name + " FROM " + "(SELECT count(*) AS total, " + what
				+ " AS v FROM Stop, StopWeather, Weather WHERE Stop.stopID = StopWeather.stopID AND Weather.id = StopWeather.weatherID GROUP BY v) t, "
				+ "(SELECT count(*) AS cancelled, " + what
				+ " AS v FROM Stop, StopWeather, Weather WHERE Stop.stopID = StopWeather.stopID AND Weather.id = StopWeather.weatherID AND realtime IS NULL GROUP BY v) c "
				+ "WHERE t.v = c.v;";
	}

	@Override
	public List<CancelledStopsData<T>> getCancelledStops() throws IOException
	{
		return readFromDatabase(getSQL());
	}

	@Override
	protected Optional<CancelledStopsData<T>> getValue(ResultSet result) throws SQLException
	{
		final CountData total = new CountData(result.getLong("total"));
		final CountData cancelled = new CountData(result.getLong("cancelled"));
		final T element = getElement(result);

		return Optional.of(new CancelledStopsData<T>(element, total, cancelled));
	}

	protected abstract String getSQL();

	protected abstract T getElement(ResultSet result) throws SQLException;
}
