package de.dhbw.studienarbeit.data.reader.data.weather.humidity;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherDBHelper;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class CancelledStopsHumidityDB extends DB<CancelledStopsData<Humidity>> implements CancelledStopsHumidity
{
	private static final String FIELD = "ROUND(humidity, 0)";
	private static final String NAME = "rounded";

	@Override
	public List<CancelledStopsData<Humidity>> getCancelledStops() throws IOException
	{
		final String sql = DelayWeatherDBHelper.buildSQLCountCancelledStops(FIELD, NAME);
		return readFromDatabase(sql);
	}

	@Override
	protected Optional<CancelledStopsData<Humidity>> getValue(ResultSet result) throws SQLException
	{
		final CountData count = new CountData(result.getLong("total"));
		final Humidity humidity = new Humidity(result.getInt(NAME));

		return Optional.of(new CancelledStopsData<Humidity>(humidity, count));
	}
}
