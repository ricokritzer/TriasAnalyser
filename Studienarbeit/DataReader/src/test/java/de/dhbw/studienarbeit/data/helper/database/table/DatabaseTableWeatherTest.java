package de.dhbw.studienarbeit.data.helper.database.table;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class DatabaseTableWeatherTest
{
	@Test
	void testCounting() throws Exception
	{
		try
		{
			long count = new DatabaseTableWeather().count();
			assertTrue(count > 0);
		}
		catch (IOException e)
		{
			fail("Unable to count weather elements." + e.getMessage());
		}
	}
}
