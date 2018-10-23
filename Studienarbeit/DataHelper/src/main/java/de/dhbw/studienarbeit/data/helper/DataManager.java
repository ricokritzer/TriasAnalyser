package de.dhbw.studienarbeit.data.helper;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DataManager
{
	private final Saver saver = new Saver();
	private final Timer timer;

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
			//time is over -> update now
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
			saver.logError(e);
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
