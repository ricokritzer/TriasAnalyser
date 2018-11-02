package de.dhbw.studienarbeit.data.repairer;

import static org.junit.Assert.assertThat;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

public class DataRepairerAppTest
{
	private String savedModelSQL;

	@Test
	void testRepairing() throws Exception
	{
		final DataRepairerApp app = new DataRepairerApp(model -> savedModelSQL = model.getSQLQuerry());

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("errors.txt")))
		{
			bw.write("2018-MM-JJJJ-hh-mm-ss\tfoo");
		}

		app.repairData();

		assertThat(savedModelSQL, Is.is("foo"));
	}
}
