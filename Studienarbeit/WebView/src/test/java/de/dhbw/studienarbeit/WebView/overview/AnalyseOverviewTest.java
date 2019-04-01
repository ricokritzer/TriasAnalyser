package de.dhbw.studienarbeit.WebView.overview;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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
		DelayCountData[] data = convertToData(new ArrayList<>());
		assertThat(data.length, is(0));
	}

	@Test
	public void arrayWith1Element() throws Exception
	{
		final List<DelayCountData> delays = new ArrayList<>();
		delays.add(new DelayCountData(new Delay(1), new CountData(1)));

		DelayCountData[] data = convertToData(delays);
		assertThat(data.length, is(1));
	}

	private DelayCountData[] convertToData(List<DelayCountData> delays)
	{
		return createAnalyseOverview().getData(delays);
	}

	private AnalyseOverview createAnalyseOverview()
	{
		return new AnalyseOverview();
	}
}
