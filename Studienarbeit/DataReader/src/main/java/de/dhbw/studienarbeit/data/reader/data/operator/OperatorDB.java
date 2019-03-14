package de.dhbw.studienarbeit.data.reader.data.operator;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.database.DB;
import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class OperatorDB extends DB<OperatorID> implements Operator
{
	private static final Logger LOGGER = Logger.getLogger(OperatorDB.class.getName());

	public final List<OperatorID> getAllOperators() throws IOException
	{
		final String sql = "SELECT DISTINCT operator FROM Station;";
		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<OperatorID> list = new ArrayList<>();
			database.select(r -> parse(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	public final List<OperatorID> getObservedOperators() throws IOException
	{
		final String sql = "SELECT DISTINCT operator FROM Station WHERE observe = true;";
		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<OperatorID> list = new ArrayList<>();
			database.select(r -> parse(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	@Override
	protected Optional<OperatorID> getValue(ResultSet result) throws SQLException
	{
		return Optional.of(new OperatorID(result.getString("operator")));
	}
}
