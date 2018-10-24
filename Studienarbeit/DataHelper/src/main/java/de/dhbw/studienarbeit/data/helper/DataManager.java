package de.dhbw.studienarbeit.data.helper;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataManager
{
	private static final Logger LOGGER = Logger.getLogger(DataManager.class.getName());

	private final Timer timer;
	private Saver saver = new TextSaver("ausgabe.txt");

	public DataManager(List<DataModel> models)
	{
		timer = new Timer();
		for (DataModel dataModel : models)
		{
			updateAndSaveAndSchedule(dataModel);
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				// ignorieren
			}
		}
	}

	public void setSaver(Saver saver)
	{
		this.saver = saver;
	}

	private void scheduleUpdate(DataModel model)
	{
		final Date now = new Date();
		final Date updateDate = model.nextUpdate();

		if (now.before(updateDate))
		{
			TimerTask task = new TimerTask()
			{
				@Override
				public void run()
				{
					updateAndSaveAndSchedule(model);
				}
			};
			timer.schedule(task, model.nextUpdate());
		}
		else
		{
			// time is over -> update now
			updateAndSaveAndSchedule(model);
		}
	}

	private void updateAndSaveAndSchedule(DataModel model)
	{
		try
		{
			model.updateData(3);
			saver.save(model);
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to update " + model.toString(), e);
		}
		finally
		{
			scheduleUpdate(model);
		}
	}

	public void stop()
	{
		timer.cancel();
	}
}
