package de.dhbw.studienarbeit.WebView.data;

import java.util.Set;
import java.util.Timer;

import de.dhbw.studienarbeit.WebView.components.DelayDiv;
import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;

public class DelayDataProvider
{
	private static final int ONE_MINUTE = 60000;
	private static DelayDataProvider instance = null;
	private Set<DelayDiv> updates;
	
	private DelayDataProvider()
	{
		
	}
	
	public DelayDataProvider getInstance()
	{
		if (instance == null)
		{
			instance = new DelayDataProvider();
			instance.updateData();
			return instance;
		}
		return instance;
	}
	
	private void updateData()
	{
		new Timer().schedule(new MyTimerTask(this::updateData), ONE_MINUTE);
	}
	
}
