package de.dhbw.studienarbeit.data.trias;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class StopTest
{
	@Test
	void testName() throws Exception
	{
		new Stop("de:07334:1714", "Testbahn", "Testziel", new Date(), Optional.ofNullable(new Date()),
				new Situation("test1", 0, "test 1")).save();
	}

	@Test
	void test2() throws Exception
	{
		new Stop("de:07334:1714", "Testbahn", "Testziel", new Date(), Optional.empty(),
				new Situation("test1", 0, "test 1")).save();
	}
}
