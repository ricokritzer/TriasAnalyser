package de.dhbw.studienarbeit.data.helper;

import java.io.IOException;

public class DataManager
{
	private Saver saver = new Saver();

	private boolean run = true;

	public DataManager(DataModel model, Updaterate rate) throws IOException
	{
		while (run)
		{
			model.updateData(3);
			saver.save(model);
			rate.waitTime();
		}
	}

	public void stop()
	{
		run = false;
	}
}
