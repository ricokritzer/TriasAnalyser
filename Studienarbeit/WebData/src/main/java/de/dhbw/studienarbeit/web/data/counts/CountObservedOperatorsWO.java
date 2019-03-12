package de.dhbw.studienarbeit.web.data.counts;

import java.io.IOException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.count.CountObservedOperators;
import de.dhbw.studienarbeit.data.reader.data.count.CountObservedOperatorsDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;

public class CountObservedOperatorsWO extends CountListWO<CountObservedOperators>
{
	protected CountObservedOperators countObservedOperators = new CountObservedOperatorsDB();

	public CountObservedOperatorsWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	/**
	 * @deprecated use setCounter() instead.
	 */
	@Deprecated
	public void setCountOperators(CountObservedOperators countObservedOperators)
	{
		this.countObservedOperators = countObservedOperators;
		update();
	}

	@Override
	protected CountData count() throws IOException
	{
		return countObservedOperators.count();
	}
}
