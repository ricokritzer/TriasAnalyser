package de.dhbw.studienarbeit.data.reader.database;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DelayTempDBTest
{
	@Test
	void testName() throws Exception
	{
		assertTrue(DelayTempDB.getDelays().size() > 1);
	}
}
