package de.dhbw.studienarbeit.web.data.time;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.Delay;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.time.DelayTimeSpanDB;
import de.dhbw.studienarbeit.data.reader.data.time.TimeSpan;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayTimeSpanWO extends Updateable
{
	private Delay<TimeSpan> delayHour = new DelayTimeSpanDB();

	private List<DelayData<TimeSpan>> data = new ArrayList<>();

	public DelayTimeSpanWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayData<TimeSpan>> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = delayHour.getDelays();
	}

	public void setDelayHour(Delay<TimeSpan> delayHour)
	{
		this.delayHour = delayHour;
		update();
	}
}
