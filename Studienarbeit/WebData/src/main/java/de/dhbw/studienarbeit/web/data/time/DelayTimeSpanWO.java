package de.dhbw.studienarbeit.web.data.time;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.time.DelayTimeSpan;
import de.dhbw.studienarbeit.data.reader.data.time.DelayTimeSpanDB;
import de.dhbw.studienarbeit.data.reader.data.time.DelayTimeSpanData;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayTimeSpanWO extends Updateable
{
	private DelayTimeSpan delayHour = new DelayTimeSpanDB();

	private List<DelayTimeSpanData> data = new ArrayList<>();

	public DelayTimeSpanWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayTimeSpanData> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = delayHour.getDelays();
	}

	public void setDelayHour(DelayTimeSpan delayHour)
	{
		this.delayHour = delayHour;
		update();
	}
}
