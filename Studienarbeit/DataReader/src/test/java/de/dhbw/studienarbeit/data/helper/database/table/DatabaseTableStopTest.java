package de.dhbw.studienarbeit.data.helper.database.table;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

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
}
