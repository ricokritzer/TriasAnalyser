package de.dhbw.studienarbeit.data.reader.database;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class OperatorTest
{
	@Test
	void testSelectingAllOperators() throws Exception
	{
		List<Operator> operators = Operator.getAllOperators();
		assertTrue(operators.size() > 0);
	}

	@Test
	void testSelectingObservedOperators() throws Exception
	{
		List<Operator> operators = Operator.getObservedOperators();
		assertTrue(operators.size() > 0);
	}
}
