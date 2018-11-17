package de.dhbw.studienarbeit.data.helper.database.table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.model.LineDB;

public class DatabaseTableLine extends DatabaseTable
{
	private static final Logger LOGGER = Logger.getLogger(DatabaseTableLine.class.getName());
	private static final String TABLE_NAME = "Line";

	@Override
	protected String getTableName()
	{
		return TABLE_NAME;
	}
}
