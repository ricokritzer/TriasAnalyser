package de.dhbw.studienarbeit.data.helper.database.model;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

public class ApiKeyDBTest
{
	@Test
	void testGetApiKeysWeather() throws Exception
	{
		List<ApiKeyDB> keys = ApiKeyDB.getApiKeys(new Operator("weather"));
		assertThat(keys.size(), Is.is(3));
	}

	@Test
	void testGetApiKeysKVV() throws Exception
	{
		List<ApiKeyDB> keys = ApiKeyDB.getApiKeys(new Operator("kvv"));
		assertThat(keys.size(), Is.is(1));
	}
}
