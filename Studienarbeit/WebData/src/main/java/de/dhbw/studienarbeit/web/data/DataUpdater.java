package de.dhbw.studienarbeit.web.data;

import java.util.Date;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;

public class DataUpdater
{
	private static final Logger LOGGER = Logger.getLogger(DataUpdater.class.getName());
	private static final DataUpdater UPDATER = new DataUpdater();
	private static final long TWO_HOURS = 2 * 60 * 60 * 1000l;

	public DataUpdater()
	{
		final Timer timer = new Timer();
		timer.schedule(new MyTimerTask(Data::new), new Date(), TWO_HOURS);
		LOGGER.log(Level.INFO, "Updates scheduled.");
	}
}
