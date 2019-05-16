package de.dhbw.studienarbeit.data.reader.data;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import de.dhbw.studienarbeit.data.reader.data.count.CountData;

public class PercentageTest
{
	@Test
	public void HundredPercent() throws Exception
	{
		final CountData data = new CountData(1);

		final Percentage percentage = new Percentage(data, data);

		assertThat(percentage.getValue(), is(100.0));
	}

	@Test
	public void FiftyPercent() throws Exception
	{
		final CountData total = new CountData(2);
		final CountData data = new CountData(1);

		final Percentage percentage = new Percentage(total, data);

		assertThat(percentage.getValue(), is(50.0));
	}
	
	@Test
	public void TwentyFivePercent() throws Exception
	{
		final CountData total = new CountData(4);
		final CountData data = new CountData(1);

		final Percentage percentage = new Percentage(total, data);

		assertThat(percentage.getValue(), is(25.0));
	}
	
	@Test
	public void TenFivePercent() throws Exception
	{
		final CountData total = new CountData(10);
		final CountData data = new CountData(1);

		final Percentage percentage = new Percentage(total, data);

		assertThat(percentage.getValue(), is(10.0));
	}
	
	@Test
	public void DevideByZeroWithoutError() throws Exception
	{
		final CountData total = new CountData(0);
		final CountData data = new CountData(1);

		new Percentage(total, data);
	}
}
