package de.dhbw.studienarbeit.data.helper.database.table;

import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.helper.database.model.ApiKeyDB;
import de.dhbw.studienarbeit.data.helper.database.model.Operator;
import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;

public class DatabaseTableApi extends DatabaseTable
{
	private static final String TABLE_NAME = "Api";

	/*
	 * use ApiKeysDB.getApiKeys(Operator operator) instead.
	 */
	@Deprecated
	public final List<ApiKey> selectApisByName(final Operator operator) throws IOException
	{
		return ApiKeyDB.getApiKeys(operator);
	}
}
