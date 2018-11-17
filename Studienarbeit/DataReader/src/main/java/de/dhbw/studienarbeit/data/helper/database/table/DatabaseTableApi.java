package de.dhbw.studienarbeit.data.helper.database.table;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;

public class DatabaseTableApi extends DatabaseTable
{
	private static final String TABLE_NAME = "Api";

	@Override
	protected String getTableName()
	{
		return TABLE_NAME;
	}

	public final List<ApiKey> selectApisByName(final String name) throws IOException
	{
		reconnectIfNeccessary();

		final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name = ?;";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
		{
			preparedStatement.setString(1, name);

			final List<ApiKey> list = new ArrayList<>();
			select(r -> ApiKey.getApiKey(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Unable to select apis by name " + name, e);
		}
	}
}
