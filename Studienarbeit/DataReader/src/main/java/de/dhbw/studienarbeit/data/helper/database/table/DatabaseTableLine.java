package de.dhbw.studienarbeit.data.helper.database.table;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

	public List<LineDB> selectLinesByNameAndDestination(String name, String destination) throws IOException
	{
		reconnectIfNeccessary();

		final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name = ? AND destination = ?;";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, destination);

			final List<LineDB> lines = new ArrayList<>();
			select(r -> getLine(r).ifPresent(lines::add), preparedStatement);
			return lines;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
