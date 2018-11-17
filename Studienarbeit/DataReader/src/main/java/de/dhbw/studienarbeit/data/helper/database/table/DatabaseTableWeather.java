package de.dhbw.studienarbeit.data.helper.database.table;

import de.dhbw.studienarbeit.data.helper.database.model.Count;

public class DatabaseTableWeather extends DatabaseTable
{
	private static final String TABLE_NAME = "Weather";

	/*
	 * use Count.countLines() instead.
	 */
	@Deprecated
	public Count count()
	{
		return Count.countWeather();
	}
}
