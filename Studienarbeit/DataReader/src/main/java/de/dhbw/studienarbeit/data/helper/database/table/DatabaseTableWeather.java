package de.dhbw.studienarbeit.data.helper.database.table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.model.WeatherDB;

public class DatabaseTableWeather extends DatabaseTable
{
	private static final Logger LOGGER = Logger.getLogger(DatabaseTableWeather.class.getName());
	private static final String TABLE_NAME = "Weather";

	@Override
	protected String getTableName()
	{
		return TABLE_NAME;
	}
}
