package de.dhbw.studienarbeit.data.reader.data.weather.clouds;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherDBHelper;
import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class DelayCloudsDB implements DelayClouds
{
	private static final Logger LOGGER = Logger.getLogger(DelayCloudsDB.class.getName());
	private static final String FIELD = "ROUND(clouds, 0)";
	private static final String NAME = "rounded";

	private static final Optional<DelayCloudsData> getDelayLine(ResultSet result)
	{
		try
		{
			final double delayMaximum = result.getDouble("delay_max");
			final double delayAverage = result.getDouble("delay_avg");
			final double wind = result.getDouble(NAME);

			return Optional.of(new DelayCloudsData(delayAverage, delayMaximum, wind));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to " + DelayCloudsDB.class.getName(), e);
			return Optional.empty();
		}
	}

	public final List<DelayCloudsData> getDelays() throws IOException
	{
		final String sql = DelayWeatherDBHelper.buildSQL(FIELD, NAME);

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayCloudsData> list = new ArrayList<>();
			database.select(r -> DelayCloudsDB.getDelayLine(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
