package de.dhbw.studienarbeit.data.reader.data.weather.pressure;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.weather.CancelledStops;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherDBHelper;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class CancelledStopsPressureDB extends DB<CancelledStopsData<Pressure>> implements CancelledStops<Pressure>
{
	private static final String FIELD = "ROUND(pressure, 0)";
	private static final String NAME = "rounded";

	@Override
	public List<CancelledStopsData<Pressure>> getCancelledStops() throws IOException
	{
		final String sql = DelayWeatherDBHelper.buildSQLCountCancelledStops(FIELD, NAME);
		return readFromDatabase(sql);
	}

	@Override
	protected Optional<CancelledStopsData<Pressure>> getValue(ResultSet result) throws SQLException
	{
		final CountData count = new CountData(result.getLong("total"));
		final Pressure humidity = new Pressure(result.getInt(NAME));

		return Optional.of(new CancelledStopsData<Pressure>(humidity, count));
	}
}
