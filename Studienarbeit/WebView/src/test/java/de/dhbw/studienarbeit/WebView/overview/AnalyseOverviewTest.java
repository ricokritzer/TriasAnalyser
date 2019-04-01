package de.dhbw.studienarbeit.WebView.overview;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.dhbw.studienarbeit.data.reader.data.request.DelayCountData;

public class AnalyseOverviewTest
{
	@Test
	public void emptyArray() throws Exception
	{
		DelayCountData[] data = convertToData(new ArrayList<>());
		assertThat(data.length, is(0));
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
