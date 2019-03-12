package de.dhbw.studienarbeit.web.data.counts;

import java.io.IOException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.count.CountOperators;
import de.dhbw.studienarbeit.data.reader.data.count.CountOperatorsDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;

public class CountObservedOperatorsWO extends CountListWO
{
	protected CountOperators countOperators = new CountOperatorsDB();

	public CountObservedOperatorsWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public void setCountOperators(CountOperators countOperators)
	{
		this.countOperators = countOperators;
		update();
	}

	@Override
	protected CountData count() throws IOException
	{
		return countOperators.countObservedOperators();
	}
}
