package de.dhbw.studienarbeit.data.reader.data.line;

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

public class DelayLineDB implements DelayLineData
{
	private static final Logger LOGGER = Logger.getLogger(DelayLineDB.class.getName());

	private final double maximum;
	private final double average;
	private final Line line;

	public DelayLineDB(double delayAverage, double delayMaximum, Line line)
	{
		this.average = delayAverage;
		this.maximum = delayMaximum;
		this.line = line;
	}

	private static final Optional<DelayLineData> getDelayLine(ResultSet result)
	{
		try
		{
			final double delayMaximum = result.getDouble("delay_max");
			final double delayAverage = result.getDouble("delay_avg");

			final LineID lineID = new LineID(result.getInt("lineID"));
			final LineName name = new LineName(result.getString("name"));
			final LineDestination destination = new LineDestination(result.getString("destination"));

			final Line line = new Line(lineID, name, destination);

			return Optional.of(new DelayLineDB(delayAverage, delayMaximum, line));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to stop.", e);
			return Optional.empty();
		}
	}

	public static final List<DelayLineData> getDelays() throws IOException
	{
		final String sql = "SELECT " + "name, destination, lineID, "
				+ "avg(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_avg, "
				+ "max(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_max "
				+ "FROM Stop, Line WHERE realTime IS NOT NULL AND Stop.lineID = Line.lineID GROUP BY Stop.lineID ORDER BY delay_avg DESC;";
		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayLineData> list = new ArrayList<>();
			database.select(r -> DelayLineDB.getDelayLine(r).ifPresent(list::add), preparedStatement);
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
	public LineID getID()
	{
		return line.getID();
	}

	@Override
	public LineName getName()
	{
		return line.getName();
	}

	@Override
	public LineDestination getDestination()
	{
		return line.getDestination();
	}

	@Override
	public String getLineName()
	{
		return getName().getValue();
	}

	@Override
	public String getLineDestination()
	{
		return getDestination().getValue();
	}
}
