package de.dhbw.studienarbeit.web.data.counts;

import java.io.IOException;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.count.CountLines;
import de.dhbw.studienarbeit.data.reader.data.count.CountLinesDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;

public class CountLinesWO extends CountListWO
{
	protected CountLines countLines = new CountLinesDB();

	public CountLinesWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public void setCountLines(CountLines countLines)
	{
		this.countLines = countLines;
		update();
	}

	@Override
	protected CountData count() throws IOException
	{
		return countLines.count();
	}
}
