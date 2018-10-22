package de.dhbw.studienarbeit.data.helper;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.swing.Timer;

public class DataManager
{
	private final Saver saver = new Saver();
	private final Timer timer;
	private final Updaterate rate;

	public DataManager(List<DataModel> models, Updaterate rate)
	{
		this.rate = rate;
		timer = new Timer(rate.getDelay(), e -> updateAndSave(models));
	}

	void updateAndSave(List<DataModel> models)
	{
		final int millisBetweenRequests = rate.getDelay() / (60 * 1000);

		for (DataModel dataModel : models)
		{
			final Date now = new Date();
			try
			{
				if (now.after(dataModel.nextUpdate()))
				{
					updateAndSave(dataModel);
					Thread.sleep(millisBetweenRequests);
				}
			}
			catch (InterruptedException e)
			{
				saver.logError(e);
			}
		}
	}

	private void updateAndSave(DataModel model)
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
	}

	public void stop()
	{
		timer.stop();
	}

	public void start()
	{
		timer.start();
	}
}
