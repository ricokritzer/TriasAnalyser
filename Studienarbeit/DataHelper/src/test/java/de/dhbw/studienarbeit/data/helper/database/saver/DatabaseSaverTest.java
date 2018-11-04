package de.dhbw.studienarbeit.data.helper.database.saver;

import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Date;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import de.dhbw.studienarbeit.data.helper.datamanagement.ApiKey;
import de.dhbw.studienarbeit.data.helper.datamanagement.DataModel;

public class DatabaseSaverTest
{
	private final static String TEXT = "bla bla bla - auf jeden Fall ein fehlerhaftes SQL-Statement";
	private final DataModel model = new DataModel()
	{
		@Override
		public Date nextUpdate()
		{
			// do nothing
			return null;
		}

		@Override
		public String getSQLQuerry()
		{
			return TEXT;
		}

		@Override
		public void updateData(ApiKey apiKey) throws IOException
		{
			// do nothing
		}
	};

	private String savedText;
	private final Saver saverMock = new Saver()
	{
		@Override
		public void save(DataSaverModel model)
		{
			savedText = model.getSQLQuerry();
		}
	};

	@Test
	void testSavingWrongSQLStatement() throws Exception
	{
		DatabaseSaver saver = new DatabaseSaver();
		saver.setSaverForErrors(saverMock);
		saver.save(model);
		assertThat(savedText, Is.is(TEXT));
	}
}
