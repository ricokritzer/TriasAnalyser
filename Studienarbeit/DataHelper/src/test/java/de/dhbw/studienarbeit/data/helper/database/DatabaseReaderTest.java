package de.dhbw.studienarbeit.data.helper.database;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

public class DatabaseReaderTest
{
	@Test
	void testSQLWith1Condition() throws Exception
	{
		final DatabaseReader reader = new DatabaseReader();
		final String sql = reader.createSQLStatement("bla", new SqlCondition("name", "test"));
		assertThat(sql, Is.is("SELECT * FROM bla WHERE name = 'test';"));
	}

	@Test
	void testSQLWith0Condition() throws Exception
	{
		final DatabaseReader reader = new DatabaseReader();
		final String sql = reader.createSQLStatement("bla");
		assertThat(sql, Is.is("SELECT * FROM bla;"));
	}

	@Test
	void testSQLWith2Condition() throws Exception
	{
		final DatabaseReader reader = new DatabaseReader();
		final String sql = reader.createSQLStatement("bla", //
				new SqlCondition("name", "test"), new SqlCondition("alter", 2));
		assertThat(sql, Is.is("SELECT * FROM bla WHERE name = 'test' AND alter = 2;"));
	}

}
