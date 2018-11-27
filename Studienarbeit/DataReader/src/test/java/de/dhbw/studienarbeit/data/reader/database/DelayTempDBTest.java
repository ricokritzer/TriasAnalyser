package de.dhbw.studienarbeit.data.reader.database;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class DelayTempDBTest
{
	@Test
	void testSelecting() throws Exception
	{
		final List<DelayTempDB> list = DelayTempDB.getDelays();
		assertTrue(list.size() > 0);
	}
}
