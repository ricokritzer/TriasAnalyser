package de.dhbw.studienarbeit.data.reader.data.weather.temperature;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherDBHelper;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class CancelledStopsTemperatureDB extends DB<CancelledStopsData<Temperature>>
		implements CancelledStopsTemperature
{
	private static final String FIELD = "ROUND(temp, 0)";
	private static final String NAME = "rounded";

	@Override
	public List<CancelledStopsData<Temperature>> getCancelledStops() throws IOException
	{
		final String sql = DelayWeatherDBHelper.buildSQLCountCancelledStops(FIELD, NAME);
		return readFromDatabase(sql);
	}

	@Override
	protected Optional<CancelledStopsData<Temperature>> getValue(ResultSet result) throws SQLException
	{
		final CountData count = new CountData(result.getLong("total"));
		final Temperature temperature = new Temperature(result.getInt(NAME));

		return Optional.of(new CancelledStopsData<Temperature>(temperature, count));
	}
}
