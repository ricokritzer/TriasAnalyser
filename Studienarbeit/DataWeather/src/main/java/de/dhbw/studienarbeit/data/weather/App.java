package de.dhbw.studienarbeit.data.weather;

import java.io.IOException;

import de.dhbw.studienarbeit.data.helper.DataManager;
import de.dhbw.studienarbeit.data.helper.Updaterate;

public class App
{
	public static void main(String[] args) throws IOException
	{
		final Weather karlsruhe = new Weather(49.01, 8.4); // Weather of Karlsruhe, DE
		new DataManager(karlsruhe, Updaterate.UPDATE_EVERY_MINUTE);
	}
}
