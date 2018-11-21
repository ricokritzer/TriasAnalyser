package de.dhbw.studienarbeit.data;

import java.io.IOException;
import java.util.logging.Level;

import de.dhbw.studienarbeit.data.helper.logging.LogLevelHelper;
import de.dhbw.studienarbeit.data.reader.database.Operator;
import de.dhbw.studienarbeit.data.reader.database.StationDB;
import de.dhbw.studienarbeit.data.trias.DataTriasApp;
import de.dhbw.studienarbeit.data.weather.DataWeatherApp;

public class App
{
	public static void main(String[] args) throws IOException
	{
		LogLevelHelper.setLogLevel(Level.INFO);

		for (Operator operator : Operator.getObservedOperators())
		{
			new DataTriasApp().startDataCollection(operator, StationDB.getObservedStations(operator));
		}

		new DataWeatherApp().startDataCollection(StationDB.getObservedStations());
	}
}
