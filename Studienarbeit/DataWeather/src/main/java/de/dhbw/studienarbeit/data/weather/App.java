package de.dhbw.studienarbeit.data.weather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.helper.DataManager;
import de.dhbw.studienarbeit.data.helper.DataModel;

public class App
{
	public static void main(String[] args) throws IOException
	{
		final List<DataModel> data = new ArrayList<>();
		data.add(new Weather(49.01, 8.4)); // Weather of Karlsruhe, DE
		data.add(new Weather(52.521918, 13.413215)); // Weather of Berlin, DE
		new DataManager(data);
	}
}
