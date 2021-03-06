package de.dhbw.studienarbeit.data.reader.data.weather;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.database.DB;

public class DelayWeatherCorrelationDB extends DB<Double>
{
	public final double getCorrelationData(String fieldname) throws IOException
	{
		final String sql = getSqlFor(fieldname);
		List<Double> read = readFromDatabase(sql);
		if (read.isEmpty())
		{
			throw new IOException("Selecting does not succeed, no elements read.");
		}

		return read.get(0).doubleValue();
	}

	public final String getSqlFor(String fieldname)
	{
		return new StringBuilder() //
				.append("SELECT sum((x-avgX) * (y-avgY)) / SQRT(sum(POW(x-avgX,2))*sum(POW(y-avgY,2))) AS correlation ")
				.append("FROM ") //
				.append("(SELECT Weather.% AS x, UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime) AS y FROM StopWeather, Stop, Weather WHERE StopWeather.stopID = Stop.stopID AND StopWeather.weatherID = Weather.id) t, ")
				.append("(SELECT avg(%) AS avgX FROM StopWeather, Weather WHERE StopWeather.weatherID = Weather.id) tX, ") //
				.append("(SELECT avg(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS avgY FROM StopWeather, Stop WHERE StopWeather.stopID = Stop.stopID) tY;")
				.toString().replaceAll("%", fieldname);
	}

	@Override
	protected Optional<Double> getValue(ResultSet result) throws SQLException
	{
		return Optional.of(Double.valueOf(result.getDouble("correlation")));
	}
}
