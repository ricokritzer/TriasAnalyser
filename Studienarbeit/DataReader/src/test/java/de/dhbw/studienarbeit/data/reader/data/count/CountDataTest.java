package de.dhbw.studienarbeit.data.reader.data.count;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CountDataTest
{
	@Test
	public void count1() throws Exception
	{
		assertThat(getCountString(1), is("1"));
	}

	@Test
	public void count10() throws Exception
	{
		assertThat(getCountString(10), is("10"));
	}

	@Test
	public void count100() throws Exception
	{
		assertThat(getCountString(100), is("100"));
	}

	@Test
	public void count1000() throws Exception
	{
		assertThat(getCountString(1000), is("1.000"));
	}

	@Test
	public void count10000() throws Exception
	{
		assertThat(getCountString(10000), is("10.000"));
	}

	@Test
	public void count100000() throws Exception
	{
		assertThat(getCountString(100000), is("100.000"));
	}

	@Test
	public void count1000000() throws Exception
	{
		assertThat(getCountString(1000000), is("1.000.000"));
	}

	private String getCountString(long number)
	{
		return new CountData(number).toString();
	}
}
