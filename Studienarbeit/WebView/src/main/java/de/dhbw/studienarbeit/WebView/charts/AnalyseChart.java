package de.dhbw.studienarbeit.WebView.charts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.syndybat.chartjs.ChartJs;

import be.ceau.chart.BarChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.options.BarOptions;
import be.ceau.chart.options.scales.BarScale;
import be.ceau.chart.options.scales.YAxis;
import be.ceau.chart.options.ticks.LinearTicks;
import de.dhbw.studienarbeit.WebView.requests.RequestGridData;
import de.dhbw.studienarbeit.data.reader.data.Delay;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.request.DelayCountData;

public class AnalyseChart
{
	private static final int exponential = 500;
	private static final int maxItems = 100;

	private BarOptions barOptions = new BarOptions();
	
	private List<RequestGridData> requests = new ArrayList<>();
	
	private int highestDelay = 0;
	private int lowestDelay = 0;

	private Color[] colors = { Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.CYAN };

	public void addDataset(RequestGridData data)
	{
		if (data.getHighestCount() > exponential)
		{
			barOptions.setScales(new BarScale().setyAxes(getYAxis()));
		}
		
		checkHighestAndLowestDelay(data);
		requests.add(data);
	}

	private void checkHighestAndLowestDelay(RequestGridData data)
	{
		if (data.getHighestDelay() > highestDelay)
		{
			highestDelay = data.getHighestDelay();
		}
		if (data.getLowestDelay() < lowestDelay)
		{
			lowestDelay = data.getLowestDelay();
		}
	}

	private Color getUniqueColor(int num)
	{
		if (num > colors.length)
		{
			return Color.BLUE;
		}
		return colors[num - 1];
	}

	public ChartJs getChart()
	{
		List<Integer> delays = new ArrayList<>();
		for (int i = lowestDelay; i <= highestDelay + 1; i++)
		{
			delays.add(i);
		}
		
		BarData barData = new BarData();
		barData.setLabels(getLabels(delays));

		for (RequestGridData data : requests)
		{
			BarDataset dataset = new BarDataset();
			dataset.setBorderColor(getUniqueColor(data.getNumber()));
			dataset.setBackgroundColor(getUniqueColor(data.getNumber()));
			dataset.setLabel(String.valueOf(data.getNumber()));
			
			for (int i : delays)
			{
				dataset.addData(data.getAddedCountAt(i));
			}
			
			barData.addDataset(dataset);
		}
		
		BarChart chart = new BarChart(barData, barOptions);
		return new ChartJs(chart.toJson());
	}

	private String[] getLabels(List<Integer> delays)
	{
		return  delays.stream().map(e -> "> " + e + " min").toArray(String[]::new);
	}

	private List<YAxis<LinearTicks>> getYAxis()
	{
		List<YAxis<LinearTicks>> axis = new ArrayList<>();
		YAxis<LinearTicks> ticks = new YAxis<>();
		ticks.setType("logarithmic");
		ticks.setStacked(false);
		axis.add(ticks);
		return axis;
	}

	protected DelayCountData[] getData(List<DelayCountData> delays)
	{
		if (delays.isEmpty())
		{
			return new DelayCountData[0];
		}

		int begin = delays.get(0).getDelayInMinutes();
		int end = delays.get(delays.size() - 1).getDelayInMinutes();

		DelayCountData[] data = new DelayCountData[(end - begin) + 1];

		for (int i = data.length - 1; i >= 0; i--)
		{
			final int idx = i;
			data[i] = delays.stream().filter(e -> e.getDelayInMinutes() == (idx + begin)).findFirst()
					.orElse(new DelayCountData(new Delay((i + begin) * 60), new CountData(0)));
		}

		int max = data.length > maxItems ? maxItems : data.length;
		DelayCountData[] shorterArray = Arrays.copyOfRange(data, 0, max);

		return shorterArray;
	}
}
