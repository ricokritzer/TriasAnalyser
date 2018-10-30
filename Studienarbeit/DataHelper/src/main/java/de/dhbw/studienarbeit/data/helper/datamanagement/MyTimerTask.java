package de.dhbw.studienarbeit.data.helper.datamanagement;

import java.util.TimerTask;

public class MyTimerTask extends TimerTask
{
	private Runnable r;

	public MyTimerTask(Runnable r)
	{
		this.r = r;
	}

	@Override
	public void run()
	{
		r.run();
	}

}
