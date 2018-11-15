package de.dhbw.studienarbeit.WebView.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.WebView.components.DelayDiv;
import de.dhbw.studienarbeit.data.helper.database.model.DelayDB;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableStop;
import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;
import edu.emory.mathcs.backport.java.util.Collections;

public class DelayDataProvider
{
	private static final int ONE_MINUTE = 60000;
	private static DelayDataProvider instance = null;
	private Set<DelayDiv> queuedDivs;
	private DelayDB delayDB;
	
	private static final Logger LOGGER = Logger.getLogger(DelayDataProvider.class.getName());
	
	private DelayDataProvider()
	{
		queuedDivs = new HashSet<>();
	}
	
	public static DelayDataProvider getInstance()
	{
		if (instance == null)
		{
			instance = new DelayDataProvider();
			instance.startUpdating();
			return instance;
		}
		return instance;
	}
	
	private void startUpdating()
	{
		new Timer().schedule(new MyTimerTask(this::updateDivs), ONE_MINUTE);
	}
	
	private void updateDivs()
	{
		List<DelayDiv> updatableDivs = new ArrayList<>();
		updatableDivs.addAll(queuedDivs);
		queuedDivs.clear();
		getNewData();
		updatableDivs.forEach(div -> div.update(delayDB));
	}
	
	private void getNewData()
	{
		try
		{
			delayDB = Optional.ofNullable(new DatabaseTableStop().selectDelay().get(0)).orElse(delayDB);
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to get delay data", e);
		}
	}
	
	public void readyForUpdate(DelayDiv div)
	{
		queuedDivs.add(div);
	}
	
	public void getDataFor(DelayDiv div)
	{
		div.update(delayDB);
	}
}
