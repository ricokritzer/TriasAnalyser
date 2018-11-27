package de.dhbw.studienarbeit.data.reader.database;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class DelayWeatherDBTest
{
	@Test
	void testName() throws Exception
	{
		final List<DelayWeatherDB> list = DelayWeatherDB.getDelays();
		assertTrue(list.size() > 0);
	}
}
