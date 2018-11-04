package de.dhbw.studienarbeit.data.helper.database.table;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class DatabaseTableStationTest
{
	@Test
	void countingStations() throws Exception
	{
		try
		{
			new DatabaseTableStation().count();
		}
		catch (IOException e)
		{
			fail("Unable to count stations" + e.getMessage());
		}
	}
}
