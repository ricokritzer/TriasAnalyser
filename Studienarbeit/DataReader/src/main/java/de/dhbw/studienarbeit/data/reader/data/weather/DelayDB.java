package de.dhbw.studienarbeit.data.reader.data.weather;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.DelayMaximum;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.database.DB;

public abstract class DelayDB<T> extends DB<DelayData<T>> implements Delays<T>
{
	@Override
	protected Optional<DelayData<T>> getValue(ResultSet result) throws SQLException
	{
		final DelayMaximum delayMaximum = new DelayMaximum(result.getDouble("delay_max"));
		final DelayAverage delayAverage = new DelayAverage(result.getDouble("delay_avg"));
		final CountData count = new CountData(result.getInt("total"));
		final T element = getElement(result);

		return Optional.of(new DelayData<T>(delayMaximum, delayAverage, count, element));
	}

	protected abstract T getElement(ResultSet result) throws SQLException;
}
