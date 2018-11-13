package de.dhbw.studienarbeit.data.helper.database.table;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class DatabaseTableLineTest
{
	@Test
	void testCounting() throws Exception
	{
		try
		{
			int count = new DatabaseTableLine().count();
			assertTrue(count > 0);
		}
		catch (IOException e)
		{
			fail("Unable to count stations" + e.getMessage());
		}
	}
}
