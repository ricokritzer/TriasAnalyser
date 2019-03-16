package de.dhbw.studienarbeit.data.reader.data.operator;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.database.DB;

public class OperatorDB extends DB<OperatorID> implements Operator
{
	public final List<OperatorID> getAllOperators() throws IOException
	{
		final String sql = "SELECT DISTINCT operator FROM Station;";
		return readFromDatabase(sql);
	}

	public final List<OperatorID> getObservedOperators() throws IOException
	{
		final String sql = "SELECT DISTINCT operator FROM Station WHERE observe = true;";
		return readFromDatabase(sql);
	}

	@Override
	protected Optional<OperatorID> getValue(ResultSet result) throws SQLException
	{
		return Optional.of(new OperatorID(result.getString("operator")));
	}
}
