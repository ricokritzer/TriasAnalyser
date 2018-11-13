package de.dhbw.studienarbeit.data.repairer;

import static org.junit.Assert.assertThat;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import de.dhbw.studienarbeit.data.helper.database.saver.Saveable;
import de.dhbw.studienarbeit.data.helper.database.saver.Saveable2;
import de.dhbw.studienarbeit.data.helper.database.saver.Saver;

public class DataRepairerAppTest
{
	private String savedModelSQL;

	@Test
	void testRepairing() throws Exception
	{
		final DataRepairerApp app = new DataRepairerApp(new Saver()
		{
			@Override
			public void save(Saveable2 model) throws IOException
			{
				savedModelSQL = model.toString();
			}

			@Override
			public void save(Saveable model) throws IOException
			{
				savedModelSQL = model.getSQLQuerry();
			}
		});

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("errors.txt")))
		{
			bw.write("2018-MM-JJJJ-hh-mm-ss\tfoo");
		}

		app.repairData();

		assertThat(savedModelSQL, Is.is("foo"));
	}
}
