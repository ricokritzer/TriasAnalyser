package de.dhbw.studienarbeit.data.reader.database;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class DelayCloudsDBTest
{
	@Test
	void testSelecting() throws Exception
	{
		final List<DelayCloudsDB> list = DelayCloudsDB.getDelays();
		assertTrue(list.size() > 0);
	}
}
