package de.dhbw.studienarbeit.data;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import de.dhbw.studienarbeit.data.helper.database.model.Operator;
import de.dhbw.studienarbeit.data.helper.database.model.StationDB;
import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableStation;
import de.dhbw.studienarbeit.data.helper.logging.LogLevelHelper;
import de.dhbw.studienarbeit.data.repairer.DataRepairerApp;
import de.dhbw.studienarbeit.data.trias.DataTriasApp;
import de.dhbw.studienarbeit.data.weather.DataWeatherApp;

public class App
{
	public static void main(String[] args) throws IOException
	{
		LogLevelHelper.setLogLevel(Level.ALL);

		final DatabaseTableStation databaseTableStation = new DatabaseTableStation();
		final List<Operator> operators = Operator.getObservedOperators();
		for (Operator operator : operators)
		{
			final List<StationDB> stationsOfOperator = databaseTableStation.selectObservedStations(operator);
			new DataWeatherApp().startDataCollection(stationsOfOperator);
			new DataTriasApp().startDataCollection(operator, stationsOfOperator);
		}

		new DataRepairerApp(new DatabaseSaver()).startDataRepairing();
	}

}
