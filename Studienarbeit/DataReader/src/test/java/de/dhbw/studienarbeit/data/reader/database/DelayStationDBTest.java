package de.dhbw.studienarbeit.data.reader.database;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class DelayStationDBTest
{
	@Test
	void test() throws Exception
	{
		List<DelayStationDB> list = DelayStationDB.getDelays();
		assertTrue(list.size() > 0);
	}
}
