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

import de.dhbw.studienarbeit.data.reader.data.station.DelayStationData;
import de.dhbw.studienarbeit.data.reader.data.station.Position;
import de.dhbw.studienarbeit.data.reader.data.station.StationName;

public class DelayStationDB implements DelayStationData
{
	private static final Logger LOGGER = Logger.getLogger(DelayStationDB.class.getName());

	private final double maximum;
	private final double average;
	private final StationName stationName;
	private final String operator;
	private final Position position;
	private final int count;

	public DelayStationDB(double maximum, double average, StationName stationName, String operator, Position position,
			int count)
	{
		super();
		this.maximum = maximum;
		this.average = average;
		this.stationName = stationName;
		this.operator = operator;
		this.position = position;
		this.count = count;
	}

	public double getMaximum()
	{
		return maximum;
	}

	public double getAverage()
	{
		return average;
	}

	@Override
	public String getStationName()
	{
		return stationName.getStationName();
	}

	@Override
	public Operator getOperator()
	{
		return new Operator(operator);
	}

	@Override
	public double getLat()
	{
		return position.getLat();
	}

	@Override
	public double getLon()
	{
		return position.getLon();
	}

	@Override
	public int getCount()
	{
		return count;
	}

	private static final Optional<DelayStationDB> getDelayLine(ResultSet result)
	{
		try
		{
			final double maximum = result.getDouble("delay_max");
			final double average = result.getDouble("delay_avg");
			final StationName name = new StationName(result.getString("name"));
			final String operator = result.getString("displayName");
			final Position position = new Position(result.getDouble("lat"), result.getDouble("lon"));
			final int count = result.getInt("count");

			return Optional.of(new DelayStationDB(maximum, average, name, operator, position, count));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to DelayStationDB.", e);
			return Optional.empty();
		}
	}

	public static final List<DelayStationDB> getDelays() throws IOException
	{
		final String sql = "SELECT " //
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
			final List<DelayStationDB> list = new ArrayList<>();
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
}
