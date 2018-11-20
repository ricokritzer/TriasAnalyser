package de.dhbw.studienarbeit.data;

import java.io.IOException;
import java.util.List;
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
		LogLevelHelper.setLogLevel(Level.WARNING);

		final List<Operator> operators = Operator.getObservedOperators();
		for (Operator operator : operators)
		{
			final List<StationDB> stationsOfOperator = StationDB.getObservedStations(operator);
			new DataWeatherApp().startDataCollection(stationsOfOperator);
			new DataTriasApp().startDataCollection(operator, stationsOfOperator);
		}
	}
}
