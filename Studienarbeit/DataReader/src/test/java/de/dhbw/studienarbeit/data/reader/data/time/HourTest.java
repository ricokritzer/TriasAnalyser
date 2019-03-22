package de.dhbw.studienarbeit.data.reader.data.time;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class HourTest
{
	@Test
	public void testString() throws Exception
	{
		assertThat(Hour.HOUR10.toString(), is("10:00 Uhr"));
	}

	@Test
	public void testBeforeTrue() throws Exception
	{
		assertThat(Hour.HOUR1.before(Hour.HOUR10), is(true));
	}

	@Test
	public void testBeforeFalse() throws Exception
	{
		assertThat(Hour.HOUR3.before(Hour.HOUR1), is(false));
	}
}
