package de.dhbw.studienarbeit.data.helper;

import java.io.IOException;

import javax.swing.Timer;

public class DataManager
{
	private Saver saver = new Saver();
	private final Timer timer;

	public DataManager(DataModel model, Updaterate rate)
	{
		timer = new Timer(rate.getDelay(), e -> updateAndSave(model));
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
