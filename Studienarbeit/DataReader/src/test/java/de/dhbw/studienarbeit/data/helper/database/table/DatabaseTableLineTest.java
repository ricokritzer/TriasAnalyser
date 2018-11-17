package de.dhbw.studienarbeit.data.helper.database.table;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import de.dhbw.studienarbeit.data.helper.database.model.Count;

public class DatabaseTableLineTest
{
	@Test
	void testCounting() throws Exception
	{
		try
		{
			Count count = new DatabaseTableLine().count();
			assertFalse(count.equals(Count.UNABLE_TO_COUNT));
		}
		catch (IOException e)
		{
			fail("Unable to count stations" + e.getMessage());
		}
	}
}
