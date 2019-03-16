package de.dhbw.studienarbeit.data.reader.data.api;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKeyData;
import de.dhbw.studienarbeit.data.reader.data.operator.OperatorID;
import de.dhbw.studienarbeit.data.reader.database.DB;

public class ApiKeyDB extends DB<ApiKeyData> implements ApiKey
{
	public final List<ApiKeyData> getApiKeys(OperatorID operator) throws IOException
	{
		final String sql = "SELECT * FROM Api WHERE name = ?;";
		return readFromDatabase(sql, e -> setValues(e, operator));
	}

	private void setValues(PreparedStatement e, OperatorID operator) throws SQLException
	{
		e.setString(1, operator.getName());
	}

	@Override
	protected Optional<ApiKeyData> getValue(ResultSet result) throws SQLException
	{
		final String key = result.getString("apiKey");
		final int requests = result.getInt("maximumRequests");
		final String url = result.getString("url");
		return Optional.of(new ApiKeyData(key, requests, url));
	}
}
