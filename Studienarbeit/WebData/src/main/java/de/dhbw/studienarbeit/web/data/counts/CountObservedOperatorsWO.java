package de.dhbw.studienarbeit.web.data.counts;

import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.CountObservedOperators;
import de.dhbw.studienarbeit.data.reader.data.count.CountObservedOperatorsDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;

public class CountObservedOperatorsWO extends CountListWO<CountObservedOperators>
{
	public CountObservedOperatorsWO(Optional<DataUpdater> updater)
	{
		counter = new CountObservedOperatorsDB();
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	/**
	 * @deprecated use setCounter() instead.
	 */
	@Deprecated
	public void setCountOperators(CountObservedOperators countObservedOperators)
	{
		setCounter(countObservedOperators);
	}
}
