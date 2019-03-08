package de.dhbw.studienarbeit.data.reader.data.vehicletype;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class DelayVehicleTypeDB implements DelayVehicleType
{
	private static final Logger LOGGER = Logger.getLogger(DelayVehicleTypeDB.class.getName());

	private static final Optional<DelayVehicleTypeData> getDelayLine(ResultSet result)
	{
		try
		{
			final double delayMaximum = result.getDouble("delay_max");
			final double delayAverage = result.getDouble("delay_avg");
			final String type = result.getString("type");

			return Optional.of(new DelayVehicleTypeData(delayAverage, delayMaximum, type));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to stop.", e);
			return Optional.empty();
		}
	}

	public final List<DelayVehicleTypeData> getDelays() throws IOException
	{
		final String sql = "SELECT " + "SUBSTRING_INDEX(name, ' ', 1 ) AS type, "
				+ "avg(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_avg, "
				+ "max(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_max "
				+ "FROM Stop, Line WHERE realTime IS NOT NULL AND Stop.lineID = Line.lineID GROUP BY type ORDER BY delay_avg DESC;";
		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayVehicleTypeData> list = new ArrayList<>();
			database.select(r -> DelayVehicleTypeDB.getDelayLine(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}