package de.dhbw.studienarbeit.data.helper.database.table;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import de.dhbw.studienarbeit.data.helper.database.model.DelayDB;
import de.dhbw.studienarbeit.data.helper.database.model.DelayLineDB;

public class DatabaseTableStopTest
{
	@Test
	void testCounting() throws Exception
	{
		try
		{
			long count = new DatabaseTableStop().count();
			assertTrue(count > 0);
		}
		catch (IOException e)
		{
			fail("Unable to count stations" + e.getMessage());
		}
	}

	@Test
	void testDelay() throws Exception
	{
		try
		{
			List<DelayDB> list = new DatabaseTableStop().selectDelay();
			assertThat(list.size(), Is.is(1));
		}
		catch (IOException e)
		{
			fail("Unable to select delay" + e.getMessage());
		}
	}

	@Test
	void testDelayNotNull() throws Exception
	{
		try
		{
			List<DelayDB> list = new DatabaseTableStop().selectDelay();
			assertThat(list.size(), Is.is(1));
		}
		catch (IOException e)
		{
			fail("Unable to select delay" + e.getMessage());
		}
	}

	@Test
	void testDelaysByName() throws Exception
	{
		try
		{
			List<DelayLineDB> list = new DatabaseTableStop().selectDelaysByLineName();
			assertTrue(list.size() > 0);
		}
		catch (IOException e)
		{
			fail("Unable to select delays by name" + e.getMessage());
		}
	}

	@Test
	void testDelaysByNameMaximumAndAverageNotMixedUp() throws Exception
	{
		try
		{
			List<DelayLineDB> list = new DatabaseTableStop().selectDelaysByLineName();
			assertTrue(list.size() > 0);
			DelayLineDB example = list.get(0);
			assertTrue(example.getAverage() <= example.getMaximum());
		}
		catch (IOException e)
		{
			fail("Unable to select delays by name" + e.getMessage());
		}
	}
}
