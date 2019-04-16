package de.dhbw.studienarbeit.web.data.line;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.Delay;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.line.DelayLineDB;
import de.dhbw.studienarbeit.data.reader.data.line.LineData;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayLineWO extends Updateable
{
	protected Delay<LineData> delayLine = new DelayLineDB();

	private List<DelayData<LineData>> data = new ArrayList<>();

	public DelayLineWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayData<LineData>> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = delayLine.getDelays();
	}

	public void setDelayLine(Delay<LineData> delayLine)
	{
		this.delayLine = delayLine;
		update();
	}
}
