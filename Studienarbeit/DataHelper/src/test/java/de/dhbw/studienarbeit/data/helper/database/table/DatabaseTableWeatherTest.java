package de.dhbw.studienarbeit.data.helper.database.table;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class DatabaseTableWeatherTest
{
	@Test
	void testCounting() throws Exception
	{
		try
		{
			new DatabaseTableWeather().count();
		}
		catch (IOException e)
		{
			fail("Unable to count weather elements." + e.getMessage());
		}
	}
}
