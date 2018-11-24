package de.dhbw.studienarbeit.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import de.dhbw.studienarbeit.data.helper.logging.LogLevelHelper;
import de.dhbw.studienarbeit.data.reader.database.Operator;
import de.dhbw.studienarbeit.data.reader.database.StationDB;
import de.dhbw.studienarbeit.data.trias.DataTriasApp;
import de.dhbw.studienarbeit.data.weather.DataWeatherApp;

public class App
{
	private static List<DataTriasApp> triasApps = new ArrayList<>();
	private static DataWeatherApp weatherApp;
	private static boolean running = false;

	public static void startDataCollection() throws IOException
	{
		LogLevelHelper.setLogLevel(Level.INFO);

		for (Operator operator : Operator.getObservedOperators())
		{
			DataTriasApp triasApp = new DataTriasApp();
			triasApps.add(triasApp);
			triasApp.startDataCollection(operator, StationDB.getObservedStations(operator));
		}
		
		weatherApp = new DataWeatherApp();
		weatherApp.startDataCollection(StationDB.getObservedStations());
		running = true;
	}
	
	public static void stopDataCollection()
	{
		for (DataTriasApp app : triasApps)
		{
			app.stopDataCollection();
		}
		triasApps.clear();
		weatherApp.stopDataCollection();
		running = false;
	}
	
	public static boolean isRunning()
	{
		return running;
	}
}
