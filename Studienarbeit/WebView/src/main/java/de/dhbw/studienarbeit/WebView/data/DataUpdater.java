package de.dhbw.studienarbeit.WebView.data;

import java.util.Date;
import java.util.Timer;

import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;

public class DataUpdater
{
	private static final DataUpdater updater = new DataUpdater();
	private static final long TWO_HOURS = 2 * 60 * 60 * 1000l;

	public DataUpdater()
	{
		final Timer timer = new Timer();
		timer.schedule(new MyTimerTask(() -> new Data()), new Date(), TWO_HOURS);
	}
}
