package de.dhbw.studienarbeit.data.reader.database;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.data.DelayVehicleTypeData;

public class DelayVehicleTypeDB implements DelayVehicleTypeData
{
	private static final Logger LOGGER = Logger.getLogger(DelayVehicleTypeDB.class.getName());

	private final double maximum;
	private final double average;
	private final String vehicleType;

	public DelayVehicleTypeDB(double delayAverage, double delayMaximum, String vehicleType)
	{
		this.average = delayAverage;
		this.maximum = delayMaximum;
		this.vehicleType = vehicleType;
	}

	public double getMaximum()
	{
		return maximum;
	}

	public double getAverage()
	{
		return average;
	}

	public String getVehicleType()
	{
		return vehicleType;
	}

	private static final Optional<DelayVehicleTypeDB> getDelayLine(ResultSet result)
	{
		try
		{
			final double delayMaximum = result.getDouble("delay_max");
			final double delayAverage = result.getDouble("delay_avg");
			final String type = result.getString("type");

			return Optional.of(new DelayVehicleTypeDB(delayAverage, delayMaximum, type));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to stop.", e);
			return Optional.empty();
		}
	}

	public static final List<DelayVehicleTypeDB> getDelays() throws IOException
	{
		final String sql = "SELECT " + "SUBSTRING_INDEX(name, ' ', 1 ) AS type, "
				+ "avg(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_avg, "
				+ "max(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_max "
				+ "FROM Stop, Line WHERE realTime IS NOT NULL AND Stop.lineID = Line.lineID GROUP BY type ORDER BY delay_avg DESC;";
		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayVehicleTypeDB> list = new ArrayList<>();
			database.select(r -> DelayVehicleTypeDB.getDelayLine(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	@Override
	public double getDelayMaximum()
	{
		return maximum;
	}

	@Override
	public double getDelayAverage()
	{
		return average;
	}
}
