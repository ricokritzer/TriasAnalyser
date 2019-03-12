package de.dhbw.studienarbeit.web.data.counts;

import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.CountLines;
import de.dhbw.studienarbeit.data.reader.data.count.CountLinesDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;

public class CountLinesWO extends CountListWO<CountLines>
{
	public CountLinesWO(Optional<DataUpdater> updater)
	{
		counter = new CountLinesDB();
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	/*
	 * @deprecated use setCounter(...) instead
	 */
	@Deprecated
	public void setCountLines(CountLines countLines)
	{
		setCounter(countLines);
	}
}
