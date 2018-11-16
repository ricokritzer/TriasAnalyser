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

	private final Optional<LineDB> getLine(ResultSet result)
	{
		try
		{
			final int lineID = result.getInt("lineID");
			final String lineName = result.getString("name");
			final String lineDestination = result.getString("destination");
			return Optional.of(new LineDB(lineID, lineName, lineDestination));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to line.", e);
			return Optional.empty();
		}
	}

	@Override
	protected String getTableName()
	{
		return TABLE_NAME;
	}
}
