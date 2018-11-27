package de.dhbw.studienarbeit.data.trias;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;

public class StationNeighbourTest
{
	@Test
	void testSaving() throws Exception
	{
		try
		{
			final StationNeighbour neighbours = new StationNeighbour("de:08212:203", "de:08212:201");
			DatabaseSaver.saveData(neighbours);
		}
		catch (Exception e)
		{
			fail("Exception thrown: " + e.getMessage());
		}
	}
}
