package de.dhbw.studienarbeit.data.helper.database.table;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.model.LineDB;

public class DatabaseTableLine extends DatabaseTable
{
	private static final Logger LOGGER = Logger.getLogger(DatabaseTableLine.class.getName());
	private static final String TABLE_NAME = "Line";
	private static final Map<LineCache, Integer> CACHE = new HashMap<>();

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

	/*
	 * @deprecated: use cached method getLineID instead.
	 */
	@Deprecated
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

	public Optional<Integer> getLineID(String lineName, String destination) throws IOException
	{
		Optional<Integer> lineIDFromCache = getLineIDFromCache(lineName, destination);
		if (lineIDFromCache.isPresent())
		{
			return lineIDFromCache;
		}

		return getLineIDFromDatabase(lineName, destination);
	}

	private Optional<Integer> getLineIDFromDatabase(String lineName, String destination) throws IOException
	{
		reconnectIfNeccessary();

		final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name = ? AND destination = ?;";
		try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			preparedStatement.setString(1, lineName);
			preparedStatement.setString(2, destination);

			final List<LineDB> lines = new ArrayList<>();
			select(r -> getLine(r).ifPresent(lines::add), preparedStatement);

			if (lines.isEmpty())
			{
				return Optional.empty();
			}

			final LineDB line = lines.get(0);
			cache(line);

			return Optional.ofNullable(lines.get(0).getLineID());
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	private void cache(LineDB line)
	{
		final LineCache lineCache = new LineCache(line.getName(), line.getDestination());
		CACHE.put(lineCache, line.getLineID());
	}

	protected Optional<Integer> getLineIDFromCache(String lineName, String destination)
	{
		final LineCache line = new LineCache(lineName, destination);
		return Optional.ofNullable(CACHE.get(line));
	}
}
