package de.dhbw.studienarbeit.data.helper.database.table;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Optional;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

public class DatabaseTableLineTest
{
	@Test
	void testCounting() throws Exception
	{
		try
		{
			long count = new DatabaseTableLine().count();
			assertTrue(count > 0);
		}
		catch (IOException e)
		{
			fail("Unable to count stations" + e.getMessage());
		}
	}

	@Test
	void lineIDFromCache() throws Exception
	{
		try
		{
			final Optional<Integer> lineID = new DatabaseTableLine().getLineID("R-Bahn RE6", "Neustadt, Hauptbahnhof");
			assertTrue(lineID.isPresent());
			assertThat(lineID.get(), Is.is(1));

			final Optional<Integer> lineIDCache = new DatabaseTableLine().getLineIDFromCache("R-Bahn RE6",
					"Neustadt, Hauptbahnhof");
			assertTrue(lineIDCache.isPresent());
			assertThat(lineIDCache.get(), Is.is(1));
		}
		catch (IOException e)
		{
			fail("Unable to get line ID" + e.getMessage());
		}
	}
}
