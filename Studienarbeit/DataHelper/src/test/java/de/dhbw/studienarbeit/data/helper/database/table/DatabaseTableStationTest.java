package de.dhbw.studienarbeit.data.helper.database.table;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DatabaseTableStationTest
{
	@Test
	void testCounting() throws Exception
	{
		try
		{
			new DatabaseTableStation().count();
		}
		catch (IOException e)
		{
			fail("Unable to count stations" + e.getMessage());
		}
	}

	@Test
	void testSelectingOperators() throws Exception
	{
		try
		{
			List<String> operators = new DatabaseTableStation().selectOperators();
			assertTrue(operators.size() > 0);
		}
		catch (IOException e)
		{
			fail("Unable to count stations" + e.getMessage());
		}
	}

	@Test
	void testSelectingObservedOperators() throws Exception
	{
		try
		{
			new DatabaseTableStation().selectObservedOperators();
		}
		catch (IOException e)
		{
			fail("Unable to count stations" + e.getMessage());
		}
	}
}
