package de.dhbw.studienarbeit.data.reader.database;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class DelayWindDBTest
{
	@Test
	void testSelecting() throws Exception
	{
		final List<DelayWindDB> list = DelayWindDB.getDelays();
		assertTrue(list.size() > 0);
	}
}
