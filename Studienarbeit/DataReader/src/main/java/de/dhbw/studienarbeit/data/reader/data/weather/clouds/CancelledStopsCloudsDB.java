package de.dhbw.studienarbeit.data.reader.data.weather.clouds;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherDBHelper;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class CancelledStopsCloudsDB extends DB<CancelledStopsData<Double>> implements CancelledStopsClouds
{
	private static final String FIELD = "ROUND(clouds, 0)";
	private static final String NAME = "rounded";

	@Override
	public List<CancelledStopsData<Double>> getCancelledStops() throws IOException
	{
		final String sql = DelayWeatherDBHelper.buildSQLCancelledStops(FIELD, NAME);
		return readFromDatabase(sql);
	}

	@Override
	protected Optional<CancelledStopsData<Double>> getValue(ResultSet result) throws SQLException
	{
		final CountData count = new CountData(result.getLong("total"));
		final double clouds = result.getDouble(NAME);

		return Optional.of(new CancelledStopsData<Double>(clouds, count));
	}
}
