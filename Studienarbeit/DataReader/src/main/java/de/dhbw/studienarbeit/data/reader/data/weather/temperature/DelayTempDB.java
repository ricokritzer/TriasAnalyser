package de.dhbw.studienarbeit.data.reader.data.weather.temperature;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.statistics.Correlatable;
import de.dhbw.studienarbeit.data.helper.statistics.Correlation;
import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherDBHelper;
import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class DelayTempDB implements Correlatable, DelayTemperatureData
{
	private static final Logger LOGGER = Logger.getLogger(DelayTempDB.class.getName());
	private static final String FIELD = "Round(temp, 0)";
	private static final String NAME = "rounded";

	private final double average;
	private final double maximum;
	private final double value;

	public DelayTempDB(double delayAverage, double delayMaximum, double value)
	{
		this.average = delayAverage;
		this.maximum = delayMaximum;
		this.value = value;
	}

	private static final Optional<DelayTemperatureData> getDelays(ResultSet result)
	{
		try
		{
			final double delayMaximum = result.getDouble("delay_max");
			final double delayAverage = result.getDouble("delay_avg");
			final double wind = result.getDouble(NAME);

			return Optional.of(new DelayTempDB(delayAverage, delayMaximum, wind));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to " + DelayTempDB.class.getName(), e);
			return Optional.empty();
		}
	}

	public static final List<DelayTemperatureData> getDelays() throws IOException
	{
		final String sql = DelayWeatherDBHelper.buildSQL(FIELD, NAME);

		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<DelayTemperatureData> list = new ArrayList<>();
			database.select(r -> DelayTempDB.getDelays(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	public static double correlationOf(List<DelayTemperatureData> temp)
	{
		double[] x = new double[temp.size()];
		double[] y = new double[temp.size()];

		for (int i = 0; i < temp.size(); i++)
		{
			x[i] = temp.get(i).getDelayAverage();
			y[i] = temp.get(i).getTemperature();
		}

		return Correlation.of(x, y);
	}

	@Override
	public double getX()
	{
		return getDelayAverage();
	}

	@Override
	public double getY()
	{
		return getTemperature();
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
	public double getTemperature()
	{
		return value;
	}
}
