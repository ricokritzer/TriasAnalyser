package de.dhbw.studienarbeit.data.helper.database.table;

public class DatabaseTableWeather extends DatabaseTable
{
	private static final String TABLE_NAME = "Weather";

	@Override
	protected String getTableName()
	{
		return TABLE_NAME;
	}
}
