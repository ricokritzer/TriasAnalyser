package de.dhbw.studienarbeit.data.reader.database;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class DelayHumidityDBTest
{
	@Test
	void testName() throws Exception
	{
		final List<DelayHumidityDB> list = DelayHumidityDB.getDelays();
		assertTrue(list.size() > 0);
	}
}
