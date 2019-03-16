package de.dhbw.studienarbeit.data.reader.database;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DB<T extends Object>
{
	public Optional<T> parse(final ResultSet result)
	{
		try
		{
			return getValue(result);
		}
		catch (SQLException e)
		{
			Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Parsing does not succeed.", e);
			return Optional.empty();
		}
	}

	protected List<T> readFromDatabase(final String sql) throws IOException
	{
		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<T> list = new ArrayList<>();
			database.select(r -> parse(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	protected List<T> readFromDatabase(String sql, SetPreparedStatementArguments setValues) throws IOException
	{
		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			setValues.accept(preparedStatement);

			final List<T> list = new ArrayList<>();
			database.select(r -> parse(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	protected abstract Optional<T> getValue(final ResultSet result) throws SQLException;
}
