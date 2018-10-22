package de.dhbw.studienarbeit.data.weather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.helper.DataManager;
import de.dhbw.studienarbeit.data.helper.DataModel;
import de.dhbw.studienarbeit.data.helper.Updaterate;

public class App
{
	public static void main(String[] args) throws IOException
	{
		final Weather karlsruhe = new Weather(49.01, 8.4); // Weather of Karlsruhe, DE
		final List<DataModel> data = new ArrayList<>();
		data.add(karlsruhe);
		new DataManager(data, Updaterate.UPDATE_EVERY_MINUTE).start();
	}
}
