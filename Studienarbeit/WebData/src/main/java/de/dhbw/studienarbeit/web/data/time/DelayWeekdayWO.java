package de.dhbw.studienarbeit.web.data.time;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.time.DelayWeekday;
import de.dhbw.studienarbeit.data.reader.data.time.DelayWeekdayDB;
import de.dhbw.studienarbeit.data.reader.data.time.DelayWeekdayData;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayWeekdayWO extends Updateable
{
	private DelayWeekday delayWeekday = new DelayWeekdayDB();

	private List<DelayWeekdayData> data = new ArrayList<>();

	public DelayWeekdayWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayWeekdayData> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = delayWeekday.getDelays();
	}

	public void setDelayWeekday(DelayWeekday delayWeekday)
	{
		this.delayWeekday = delayWeekday;
		update();
	}
}
