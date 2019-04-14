package de.dhbw.studienarbeit.WebView.charts;

import java.math.BigDecimal;
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
import de.dhbw.studienarbeit.data.reader.data.Delay;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.request.DelayCountData;

public class AnalyseChart
{
	private static final int exponential = 500;
	private static final int maxItems = 100;

	private BarData barData = new BarData();
	private BarOptions barOptions = new BarOptions();

	private int countDatasets = 1;
	private Color[] colors = { Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.CYAN };

	public void addDataset(List<DelayCountData> delays)
	{
		DelayCountData[] data = getData(delays);
		BigDecimal[] delaysAdded = getDelays(data);

		barData.setLabels(getLabels(data));

		BarDataset dataset = new BarDataset().setData(delaysAdded).setLabel(String.valueOf(countDatasets))
				.setBackgroundColor(getUniqueColor()).setBorderColor(getUniqueColor());

		barData.addDataset(dataset);

		if (greatestValueIsMoreThan(exponential, delaysAdded))
		{
			barOptions.setScales(new BarScale().setyAxes(getYAxis()));
		}
		
		countDatasets++;
	}

	private Color getUniqueColor()
	{
		if (countDatasets > colors.length)
		{
			return Color.BLUE;
		}
		return colors[countDatasets - 1];
	}

	public ChartJs getChart()
	{
		BarDataset zeroData = new BarDataset().setBackgroundColor(Color.BLACK).setBorderColor(Color.BLACK);
		for (int i = 0; i < 100; i++)
		{
			zeroData.addData(0);
		}
		barData.addDataset(zeroData);
		
		BarChart chart = new BarChart(barData, barOptions);
		return new ChartJs(chart.toJson());
	}

	private boolean greatestValueIsMoreThan(int max, BigDecimal[] delaysAdded)
	{
		return delaysAdded[0].compareTo(BigDecimal.valueOf(max)) > 0;
	}

	private BigDecimal[] getDelays(DelayCountData[] data)
	{
		BigDecimal[] delayArray = new BigDecimal[data.length];
		delayArray[delayArray.length - 1] = BigDecimal.valueOf(data[data.length - 1].getCountValue());

		for (int i = delayArray.length - 2; i >= 0; i--)
		{
			delayArray[i] = BigDecimal.valueOf(data[i].getCountValue()).add(delayArray[i + 1]);
		}

		return delayArray;
	}

	private String[] getLabels(DelayCountData[] data)
	{
		return Arrays.asList(data).stream().map(e -> "> " + e.getDelayInMinutes() + " min").toArray(String[]::new);
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
