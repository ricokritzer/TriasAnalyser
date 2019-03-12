package de.dhbw.studienarbeit.data.reader.data.api;

import java.io.IOException;
import java.util.List;

import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKeyData;
import de.dhbw.studienarbeit.data.reader.data.operator.OperatorID;

public interface ApiKey
{
	List<ApiKeyData> getApiKeys(OperatorID operator) throws IOException;
}
