package de.dhbw.studienarbeit.WebView.data;

import java.util.Date;
import java.util.Timer;

import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;

public abstract class DataProvider
{
	protected static final int ONE_MINUTE = 60000;

	protected abstract void updateDivs();

	protected abstract void updateBean();

	protected void startUpdating()
	{
		final Timer timer = new Timer();
		timer.schedule(new MyTimerTask(this::updateDivs), new Date(), ONE_MINUTE);
	}
}
