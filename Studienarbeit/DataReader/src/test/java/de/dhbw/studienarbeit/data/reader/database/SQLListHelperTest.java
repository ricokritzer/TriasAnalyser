package de.dhbw.studienarbeit.data.reader.database;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SQLListHelperTest
{
	@Test
	public void emptyList() throws Exception
	{
		assertThat(SQLListHelper.createSQLFor("test", createStringList(0)), is(""));
	}

	@Test
	public void listWith1Element() throws Exception
	{
		assertThat(SQLListHelper.createSQLFor("test", createStringList(1)), is("test IN (?)"));
	}

	@Test
	public void listWith2Element() throws Exception
	{
		assertThat(SQLListHelper.createSQLFor("test", createStringList(2)), is("test IN (?, ?)"));
	}

	private List<String> createStringList(int count)
	{
		final List<String> list = new ArrayList<>();
		for (int i = 0; i < count; i++)
		{
			list.add("foo");
		}
		return list;
	}
}
