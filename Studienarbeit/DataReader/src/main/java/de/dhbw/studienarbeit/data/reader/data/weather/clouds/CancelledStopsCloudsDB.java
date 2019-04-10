package de.dhbw.studienarbeit.data.reader.data.weather.clouds;

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

public class CancelledStopsCloudsDB extends DB<CancelledStopsData<Clouds>> implements CancelledStops<Clouds>
{
	private static final String FIELD = "ROUND(clouds, 0)";
	private static final String NAME = "rounded";

	@Override
	public List<CancelledStopsData<Clouds>> getCancelledStops() throws IOException
	{
		final String sql = DelayWeatherDBHelper.buildSQLCountCancelledStops(FIELD, NAME);
		return readFromDatabase(sql);
	}

	@Override
	protected Optional<CancelledStopsData<Clouds>> getValue(ResultSet result) throws SQLException
	{
		final CountData count = new CountData(result.getLong("total"));
		final Clouds clouds = new Clouds(result.getInt(NAME));

		return Optional.of(new CancelledStopsData<Clouds>(clouds, count));
	}
}
