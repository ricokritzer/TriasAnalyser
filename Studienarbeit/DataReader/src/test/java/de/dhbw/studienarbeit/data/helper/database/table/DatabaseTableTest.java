package de.dhbw.studienarbeit.data.helper.database.table;

import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

import de.dhbw.studienarbeit.data.helper.database.SqlCondition;
import de.dhbw.studienarbeit.data.helper.database.ValueEquals;

public class DatabaseTableTest
{
	private DatabaseTable databaseTable() throws IOException
	{
		return new DatabaseTable()
		{
			@Override
			public int count(SqlCondition... conditions)
			{
				return 0;
			}

			@Override
			protected String getTableName()
			{
				return "foo";
			}
		};
	}

	@Test
	void testSQLWith0Condition() throws Exception
	{
		final String sql = databaseTable().createSQLStatement("bla");
		assertThat(sql, Is.is("SELECT * FROM bla;"));
	}

	@Test
	void testSQLWith1Condition() throws Exception
	{
		final String sql = databaseTable().createSQLStatement("bla", new ValueEquals("name", "test"));
		assertThat(sql, Is.is("SELECT * FROM bla WHERE name = 'test';"));
	}

	@Test
	void testSQLWith2Condition() throws Exception
	{
		final String sql = databaseTable().createSQLStatement("bla", //
				new ValueEquals("name", "test"), new ValueEquals("alter", 2));
		assertThat(sql, Is.is("SELECT * FROM bla WHERE name = 'test' AND alter = 2;"));
	}
}
