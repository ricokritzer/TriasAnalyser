package de.dhbw.studienarbeit.data.helper.database.table;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.dhbw.studienarbeit.data.helper.database.model.Count;
import de.dhbw.studienarbeit.data.helper.database.model.Operator;
import de.dhbw.studienarbeit.data.helper.database.model.StationDB;

public class DatabaseTableStationTest
{
	@Test
	void testCounting() throws Exception
	{
		try
		{
			Count count = new DatabaseTableStation().count();
			assertFalse(count.equals(Count.UNABLE_TO_COUNT));
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
			List<Operator> operators = new DatabaseTableStation().selectOperators();
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
			List<Operator> operators = new DatabaseTableStation().selectObservedOperators();
			assertTrue(operators.size() > 0);
		}
		catch (IOException e)
		{
			fail("Unable to count stations" + e.getMessage());
		}
	}

	@Test
	void testSelectingObservedKVV() throws Exception
	{
		try
		{
			List<StationDB> stations = new DatabaseTableStation().selectObservedStations(new Operator("kvv"));
			assertTrue(stations.size() > 0);
		}
		catch (IOException e)
		{
			fail("Unable to count stations" + e.getMessage());
		}
	}
}
