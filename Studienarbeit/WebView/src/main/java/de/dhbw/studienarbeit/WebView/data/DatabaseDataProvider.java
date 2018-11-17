package de.dhbw.studienarbeit.WebView.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.WebView.components.DatabaseDiv;
import de.dhbw.studienarbeit.data.helper.database.model.CountDB;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTable;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableLine;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableStation;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableStop;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableWeather;

public class DatabaseDataProvider extends DataProvider
{
	private static DatabaseDataProvider instance = null;
	private Set<DatabaseDiv> queuedDivs = new HashSet<>();
	private DatabaseBean databaseBean = DatabaseBean.EMPTY;

	private static final Logger LOGGER = Logger.getLogger(DatabaseDataProvider.class.getName());

	public static DatabaseDataProvider getInstance()
	{
		if (instance == null)
		{
			instance = new DatabaseDataProvider();
			instance.startUpdating();
			return instance;
		}
		return instance;
	}

	public void readyForUpdate(DatabaseDiv div)
	{
		queuedDivs.add(div);
	}

	public void getDataFor(DatabaseDiv div)
	{
		div.update(databaseBean);
	}

	@Override
	protected void updateDivs()
	{
		List<DatabaseDiv> updatableDivs = new ArrayList<>();
		updatableDivs.addAll(queuedDivs);
		queuedDivs.clear();

		updateBean();
		updatableDivs.forEach(div -> div.update(databaseBean));
	}

	@Override
	protected void updateBean()
	{
		final CountDB countStation = getCountOf(new DatabaseTableStation());
		final CountDB countLines = getCountOf(new DatabaseTableLine());
		final CountDB countStops = getCountOf(new DatabaseTableStop());
		final CountDB countWeather = getCountOf(new DatabaseTableWeather());

		databaseBean = new DatabaseBean(countStation, countLines, countStops, countWeather, new Date());
	}

	private CountDB getCountOf(DatabaseTable table)
	{
		try
		{
			return table.count();
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to update.", e);
			return CountDB.UNABLE_TO_COUNT;
		}
	}
}
