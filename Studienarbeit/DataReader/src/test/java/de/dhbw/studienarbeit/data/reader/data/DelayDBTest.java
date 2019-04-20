package de.dhbw.studienarbeit.data.reader.data;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public abstract class DelayDBTest<T>
{

	@Test
	public void sqlContainsTotal() throws Exception
	{
		assertTrue(getClassUnderTest().getSQL().contains("count(*) AS total"));
	}

	@Test
	public void sqlContainsDelayAverage() throws Exception
	{
		assertTrue(getClassUnderTest().getSQL()
				.contains("avg(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_avg"));
	}

	@Test
	public void sqlContainsDelayMaximum() throws Exception
	{
		assertTrue(getClassUnderTest().getSQL()
				.contains("max(UNIX_TIMESTAMP(realTime) - UNIX_TIMESTAMP(timeTabledTime)) AS delay_max"));
	}

	protected abstract DelayDB<T> getClassUnderTest();
}
