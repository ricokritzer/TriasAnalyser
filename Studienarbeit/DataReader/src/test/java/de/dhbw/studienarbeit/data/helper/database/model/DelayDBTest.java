package de.dhbw.studienarbeit.data.helper.database.model;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

public class DelayDBTest
{
	@Test
	void testSelectingDelaysByName() throws Exception
	{
		List<DelayDB> list = DelayDB.getDelay();
		assertThat(list.size(), Is.is(1));
	}
}
