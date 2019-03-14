package de.dhbw.studienarbeit.data.reader.database;

import java.sql.ResultSet;
import java.sql.SQLException;
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

	protected abstract Optional<T> getValue(final ResultSet result) throws SQLException;
}
