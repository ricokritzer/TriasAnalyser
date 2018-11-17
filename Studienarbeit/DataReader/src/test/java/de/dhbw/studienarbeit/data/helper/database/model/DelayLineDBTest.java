package de.dhbw.studienarbeit.data.helper.database.model;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class DelayLineDBTest
{
	@Test
	void testSelectingDelaysByName() throws Exception
	{
		List<DelayLineDB> list = DelayLineDB.getDelaysByLineName();
		assertTrue(list.size() > 0);
	}
}
