package de.dhbw.studienarbeit.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	
	private static final Logger LOGGER = Logger.getLogger(App.class.getName());
	
	public static void main(String[] args) throws IOException
	{
		if (args[0].equals("stop"))
		{
			App.stopDataCollection();
		}
		else if (args[0].equals("start") && args.length > 1)
		{
			App.startDataCollection(args[1]);
		}
	}
	
	public static void startDataCollection(String fileName) throws IOException
	{
		if (!fileName.isEmpty())
		{
			Handler handler = new FileHandler(fileName, true);
			Logger.getLogger("").addHandler(handler);
		}
		LogLevelHelper.setLogLevel(Level.WARNING);

		for (Operator operator : Operator.getObservedOperators())
		{
			DataTriasApp triasApp = new DataTriasApp();
			triasApps.add(triasApp);
			triasApp.startDataCollection(operator, StationDB.getObservedStations(operator));
		}
		
		weatherApp = new DataWeatherApp();
		weatherApp.startDataCollection(StationDB.getObservedStations());
		running = true;
		LOGGER.log(Level.INFO, "Data collection started");
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
		LOGGER.log(Level.INFO, "Data collection stopped");
	}
	
	public static boolean isRunning()
	{
		return running;
	}
}
