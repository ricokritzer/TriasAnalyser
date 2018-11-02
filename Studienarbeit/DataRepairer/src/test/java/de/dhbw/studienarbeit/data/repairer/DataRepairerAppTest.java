package de.dhbw.studienarbeit.data.repairer;

import static org.junit.Assert.assertThat;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import de.dhbw.studienarbeit.data.helper.database.Saver;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataModel;

public class DataRepairerAppTest
{
	private String savedModelSQL;

	@Test
	void testRepairing() throws Exception
	{
		Saver saverMock = new Saver()
		{
			@Override
			public void save(DataModel model)
			{
				savedModelSQL = model.getSQLQuerry();
			}
		};

		final DataRepairerApp app = new DataRepairerApp(saverMock);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("errors.txt")))
		{
			bw.write("2018-MM-JJJJ-hh-mm-ss\tfoo");
		}

		app.repairData();

		assertThat(savedModelSQL, Is.is("foo"));
	}
}
