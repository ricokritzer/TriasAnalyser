package de.dhbw.studienarbeit.WebView.overview;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.dhbw.studienarbeit.data.reader.data.Delay;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.request.DelayCountData;

public class AnalyseOverviewTest
{
	@Test
	public void emptyArray() throws Exception
	{
		final DelayCountData[] data = convertToData(new ArrayList<>());

		assertThat(data.length, is(0));
	}

	@Test
	public void arrayWith1Element() throws Exception
	{
		final DelayCountData delayCountData = createDelayCountData();
		final List<DelayCountData> delays = new ArrayList<>();
		delays.add(delayCountData);

		final DelayCountData[] data = convertToData(delays);

		assertTrue(data.length > 0);
		assertThat(data[data.length - 1].getDelayValue(), is(delayCountData.getDelayValue()));
	}

	@Test
	public void arrayWith1NegativeElement() throws Exception
	{
		final List<DelayCountData> delays = new ArrayList<>();
		delays.add(createDelayCountDataWithDelay(-1));

		final DelayCountData[] data = convertToData(delays);

		assertTrue(data.length > 0);
		assertThat(data[0].getDelayValue(), is(-1.0));
	}

	@Test
	public void arrayWith2Elements() throws Exception
	{
		final List<DelayCountData> delays = new ArrayList<>();
		delays.add(createDelayCountDataWithDelay(0.0));
		delays.add(createDelayCountDataWithDelay(1.0));

		final DelayCountData[] data = convertToData(delays);

		assertThat(data.length, is(2));
		assertThat(data[0].getDelayValue(), is(0.0));
		assertThat(data[1].getDelayValue(), is(1.0));
	}

	@Test
	public void arrayWith2Elements1Negative() throws Exception
	{
		final List<DelayCountData> delays = new ArrayList<>();
		delays.add(new DelayCountData(new Delay(-1.0), new CountData(1)));
		delays.add(new DelayCountData(new Delay(0.0), new CountData(1)));

		final DelayCountData[] data = convertToData(delays);

		assertThat(data.length, is(2));
		assertThat(data[0].getDelayValue(), is(-1.0));
		assertThat(data[1].getDelayValue(), is(0.0));
	}

	@Test
	public void arrayWith2Elements2Negative() throws Exception
	{
		final List<DelayCountData> delays = new ArrayList<>();
		delays.add(createDelayCountDataWithDelay(-2.0));
		delays.add(createDelayCountDataWithDelay(-1.0));

		final DelayCountData[] data = convertToData(delays);

		assertTrue(data.length > 1);
		assertThat(data[0].getDelayValue(), is(-2.0));
		assertThat(data[1].getDelayValue(), is(-1.0));
	}

	@Test
	public void arrayWith2ElementsWithSpaceBetweenElements() throws Exception
	{
		final List<DelayCountData> delays = new ArrayList<>();
		delays.add(createDelayCountDataWithDelay(0.0));
		delays.add(createDelayCountDataWithDelay(2.0));

		final DelayCountData[] data = convertToData(delays);

		assertThat(data.length, is(3));
		assertThat(data[0].getDelayValue(), is(0.0));
		assertThat(data[1].getDelayValue(), is(1.0));
		assertThat(data[2].getDelayValue(), is(2.0));
	}

	@Test
	public void arrayWith2ElementsWithSpaceBetweenElements1Negative() throws Exception
	{
		final List<DelayCountData> delays = new ArrayList<>();
		delays.add(createDelayCountDataWithDelay(-1.0));
		delays.add(createDelayCountDataWithDelay(2.0));

		final DelayCountData[] data = convertToData(delays);

		assertThat(data.length, is(4));
		assertThat(data[0].getDelayValue(), is(-1.0));
		assertThat(data[1].getDelayValue(), is(0.0));
		assertThat(data[2].getDelayValue(), is(1.0));
		assertThat(data[3].getDelayValue(), is(2.0));
	}

	@Test
	public void arrayWith2ElementsWithSpaceBetweenElements2Negative() throws Exception
	{
		final List<DelayCountData> delays = new ArrayList<>();
		delays.add(createDelayCountDataWithDelay(-10));
		delays.add(createDelayCountDataWithDelay(-8));

		final DelayCountData[] data = convertToData(delays);

		assertThat(data.length, is(3));
		assertThat(data[0].getDelayValue(), is(-10.0));
		assertThat(data[1].getDelayValue(), is(-9.0));
		assertThat(data[2].getDelayValue(), is(-8.0));
	}

	private DelayCountData[] convertToData(List<DelayCountData> delays)
	{
		return createAnalyseOverview().getData(delays);
	}

	private AnalyseOverview createAnalyseOverview()
	{
		return new AnalyseOverview();
	}

	private DelayCountData createDelayCountData()
	{
		return createDelayCountDataWithDelay(1);
	}

	private DelayCountData createDelayCountDataWithDelay(double delay)
	{
		return new DelayCountData(new Delay(delay), new CountData(1));
	}
}
