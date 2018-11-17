package de.dhbw.studienarbeit.WebView.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.WebView.components.DelayDiv;
import de.dhbw.studienarbeit.data.reader.database.DelayDB;

public class DelayDataProvider extends DataProvider
{
	private static DelayDataProvider instance = null;
	private Set<DelayDiv> queuedDivs = new HashSet<>();
	private DelayBean delayBean = new DelayBean(0, 0, 0, new Date());

	private static final Logger LOGGER = Logger.getLogger(DelayDataProvider.class.getName());

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

	public void readyForUpdate(DelayDiv div)
	{
		queuedDivs.add(div);
	}

	public void getDataFor(DelayDiv div)
	{
		div.update(delayBean);
	}

	private DelayBean convertToBean(DelayDB delay)
	{
		return new DelayBean(delay.getSum(), delay.getMaximum(), delay.getAverage(), new Date());
	}

	@Override
	protected void updateDivs()
	{
		List<DelayDiv> updatableDivs = new ArrayList<>();
		updatableDivs.addAll(queuedDivs);
		queuedDivs.clear();

		updateBean();
		updatableDivs.forEach(div -> div.update(delayBean));
	}

	@Override
	protected void updateBean()
	{
		try
		{
			final List<DelayDB> delays = DelayDB.getDelay();
			delays.stream().findFirst().ifPresent(db -> delayBean = convertToBean(db));
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to update delay data.", e);
		}
	}
}
