package de.dhbw.studienarbeit.web.data.counts;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.Count;
import de.dhbw.studienarbeit.data.reader.data.count.CountOperators;
import de.dhbw.studienarbeit.data.reader.data.count.CountOperatorsDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;

public class CountOperatorsWO extends CountListWO
{
	protected CountOperators countOperators = new CountOperatorsDB();

	public CountOperatorsWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	@Override
	protected void updateData() throws IOException
	{
		final Count stations = countOperators.countOperators();
		final Date lastUpdate = new Date();
		add(new CountWO(stations, lastUpdate));
	}

	public void setCountOperators(CountOperators countOperators)
	{
		this.countOperators = countOperators;
		update();
	}
}
