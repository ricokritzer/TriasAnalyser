package de.dhbw.studienarbeit.web.data;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.Is;
import org.junit.Test;

public class DataUpdaterTest
{
	private boolean isUpdated = false;

	@Test
	public void testUpdating() throws Exception
	{
		DataUpdater.scheduleUpdate((() -> isUpdated = true), 10, "DataUpdaterTest");
		Thread.sleep(5);
		assertThat(isUpdated, Is.is(true));
	}
}
