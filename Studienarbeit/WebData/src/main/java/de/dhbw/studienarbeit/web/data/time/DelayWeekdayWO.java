package de.dhbw.studienarbeit.web.data.time;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.Delay;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.time.DelayWeekdayDB;
import de.dhbw.studienarbeit.data.reader.data.time.Weekday;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelayWeekdayWO extends Updateable
{
	private Delay<Weekday> delayWeekday = new DelayWeekdayDB();

	private List<DelayData<Weekday>> data = new ArrayList<>();

	public DelayWeekdayWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));
	}

	public List<DelayData<Weekday>> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = delayWeekday.getDelays();
	}

	public void setDelayWeekday(Delay<Weekday> delayWeekday)
	{
		this.delayWeekday = delayWeekday;
		update();
	}
}
