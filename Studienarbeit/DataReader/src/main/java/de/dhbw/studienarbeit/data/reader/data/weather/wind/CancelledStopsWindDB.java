package de.dhbw.studienarbeit.data.reader.data.weather.wind;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherDBHelper;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class CancelledStopsWindDB extends DB<CancelledStopsData<Wind>> implements CancelledStopsWind
{
	private static final String FIELD = "ROUND(wind, 0)";
	private static final String NAME = "rounded";

	@Override
	public List<CancelledStopsData<Wind>> getCancelledStops() throws IOException
	{
		final String sql = DelayWeatherDBHelper.buildSQLCountCancelledStops(FIELD, NAME);
		return readFromDatabase(sql);
	}

	@Override
	protected Optional<CancelledStopsData<Wind>> getValue(ResultSet result) throws SQLException
	{
		final CountData count = new CountData(result.getLong("total"));
		final Wind wind = new Wind(result.getInt(NAME));

		return Optional.of(new CancelledStopsData<Wind>(wind, count));
	}
}
