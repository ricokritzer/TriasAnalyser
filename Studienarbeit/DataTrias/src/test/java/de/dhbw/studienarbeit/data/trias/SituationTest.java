package de.dhbw.studienarbeit.data.trias;

import org.junit.jupiter.api.Test;

import de.dhbw.studienarbeit.data.helper.database.saver.DatabaseSaver;

public class SituationTest
{
	@Test
	void testSavingSituation() throws Exception
	{
		final Situation situation = new Situation("Testsituation");
		new DatabaseSaver().save(situation);
	}
}
