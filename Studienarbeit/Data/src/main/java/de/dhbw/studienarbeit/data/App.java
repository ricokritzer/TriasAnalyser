package de.dhbw.studienarbeit.data;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.logging.LogLevelHelper;
import de.dhbw.studienarbeit.data.reader.data.operator.OperatorDB;
import de.dhbw.studienarbeit.data.reader.data.operator.OperatorID;
import de.dhbw.studienarbeit.data.reader.data.station.ObservedStation;
import de.dhbw.studienarbeit.data.reader.data.station.ObservedStationDB;
import de.dhbw.studienarbeit.data.trias.DataTriasApp;
import de.dhbw.studienarbeit.data.weather.DataWeatherApp;

public class App
{
	private static final Logger LOGGER = Logger.getLogger(App.class.getName());

	public static void main(String[] args) throws IOException
	{
		String fileName = "";
		if (args.length > 0)
		{
			fileName = args[0];
		}
		else
		{
			System.exit(1);
		}

		Handler handler = new FileHandler(fileName, true);
		Logger.getLogger("").addHandler(handler);
		LogLevelHelper.setLogLevel(Level.WARNING);

		final ObservedStation observedStation = new ObservedStationDB();

		for (OperatorID operator : new OperatorDB().getObservedOperators())
		{
			new DataTriasApp().startDataCollection(operator, observedStation.getObservedStations(operator));
		}

		new DataWeatherApp().startDataCollection(observedStation.getObservedStations());
		LOGGER.log(Level.INFO, "Data collection started");
	}
}