package de.dhbw.studienarbeit.data.weather;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.helper.DataManager;
import de.dhbw.studienarbeit.data.helper.DataModel;
import de.dhbw.studienarbeit.data.helper.DatabaseSaver;

public class App
{
	public static void main(String[] args) throws IOException, SQLException, ReflectiveOperationException
	{
		final List<DataModel> data = new ArrayList<>();
		data.add(new Weather("de:test:Karlsruhe", 49.01, 8.4)); // Weather of Karlsruhe, DE
		data.add(new Weather("de:test:Berlin", 52.521918, 13.413215)); // Weather of Berlin, DE
		DataManager manager = new DataManager(data);
		manager.setSaver(new DatabaseSaver());
	}
}
