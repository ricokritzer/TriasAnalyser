package de.dhbw.studienarbeit.data;

import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.helper.database.model.StationDB;
import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableStation;
import de.dhbw.studienarbeit.data.repairer.DataRepairerApp;
import de.dhbw.studienarbeit.data.trias.DataTriasApp;
import de.dhbw.studienarbeit.data.weather.DataWeatherApp;

public class App
{
	public static void main(String[] args) throws IOException
	{
		final List<StationDB> stations = new DatabaseTableStation().selectObservedStations();
		new DataWeatherApp().startDataCollection(stations);
		new DataTriasApp().startDataCollection(stations);
		new DataRepairerApp(new DatabaseSaver()).startDataRepairing();
	}
}
