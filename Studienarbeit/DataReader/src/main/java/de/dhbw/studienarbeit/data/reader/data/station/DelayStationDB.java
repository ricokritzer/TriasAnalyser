package de.dhbw.studienarbeit.data.reader.data.station;

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

public class DelayStationDB implements DelayStationData
{
	private static final Logger LOGGER = Logger.getLogger(DelayStationDB.class.getName());

	private final double maximum;
	private final double average;
	private final StationID stationID;
	private final StationName stationName;
	private final String operator;
	private final Position position;
	private final int count;

	public DelayStationDB(double maximum, double average, StationID stationID, StationName stationName, String operator,
			Position position, int count)
	{
		super();
		this.maximum = maximum;
		this.average = average;
		this.stationID = stationID;
		this.stationName = stationName;
		this.operator = operator;
		this.position = position;
		this.count = count;
	}

	@Override
	public OperatorName getOperator()
	{
		return new OperatorName(operator);
	}

	@Override
	public int getCount()
	{
		return count;
	}

	private static final Optional<DelayStationData> getDelayLine(ResultSet result)
	{
		try
		{
			final double maximum = result.getDouble("delay_max");
			final double average = result.getDouble("delay_avg");
			final StationID stationID = new StationID(result.getString("stationID"));
			final StationName name = new StationName(result.getString("name"));
			final String operator = result.getString("displayName");
			final Position position = new Position(result.getDouble("lat"), result.getDouble("lon"));
			final int count = result.getInt("count");

			return Optional.of(new DelayStationDB(maximum, average, stationID, name, operator, position, count));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to DelayStationDB.", e);
			return Optional.empty();
		}
	}

	public static final List<DelayStationData> getDelays() throws IOException
	{
		final String sql = "SELECT " //
				+ "stationID, " //
				+ "name, " //
				+ "displayName, " //
				+ "lat, " //
				+ "lon, " //
				+ "count(*) AS count, " //
				+ "avg(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_avg, "
				+ "max(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_max "
				+ "FROM Stop, Station, Operator WHERE realTime IS NOT NULL AND Station.operator = Operator.operator AND Stop.stationID = Station.stationID GROUP BY Station.stationID ORDER BY delay_avg DESC;";
		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayStationData> list = new ArrayList<>();
			database.select(r -> DelayStationDB.getDelayLine(r).ifPresent(list::add), preparedStatement);
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

	@Override
	public StationName getName()
	{
		return stationName;
	}

	@Override
	public Position getPosition()
	{
		return position;
	}

	@Override
	public StationID getStationID()
	{
		return stationID;
	}
}
