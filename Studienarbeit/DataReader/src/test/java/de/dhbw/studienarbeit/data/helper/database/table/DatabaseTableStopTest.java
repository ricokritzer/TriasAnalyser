package de.dhbw.studienarbeit.data.helper.database.table;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import de.dhbw.studienarbeit.data.helper.database.model.DelayDB;

public class DatabaseTableStopTest
{
	@Test
	void testCounting() throws Exception
	{
		try
		{
			new DatabaseTableStop().count();
		}
		catch (IOException e)
		{
			fail("Unable to count stations" + e.getMessage());
		}
	}

	@Test
	void gettingDelayTwiceShouldBeUpdatedOnlyOnce() throws Exception
	{
		try
		{
			final DelayDB object = new DatabaseTableStop().getDelay();
			final DelayDB object2 = new DatabaseTableStop().getDelay();

			assertThat(object, Is.is(object2));
		}
		catch (IOException e)
		{
			fail("Unable to get delay." + e.getMessage());
		}
	}
}
