package de.dhbw.studienarbeit.data.reader.database;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

public class OperatorTest
{
	@Test
	void testSelectingAllOperators() throws Exception
	{
		List<Operator> operators = Operator.getAllOperators();
		assertThat(operators.size(), Is.is(1));
	}

	@Test
	void testSelectingObservedOperators() throws Exception
	{
		List<Operator> operators = Operator.getObservedOperators();
		assertThat(operators.size(), Is.is(1));
	}
}
