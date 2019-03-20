package de.dhbw.studienarbeit.WebView.charts;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import be.ceau.chart.BarChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.options.BarOptions;
import de.dhbw.studienarbeit.web.data.Data;

public class StopAndWeatherCountChart extends AbstractChart
{
	private static final long serialVersionUID = 1L;

	protected String getChart()
	{
		BarDataset datasetStop = new BarDataset().setData(getStopData()).setLabel("Stops Zuwachs").setBorderColor(Color.BLUE)
				.setBorderWidth(2).setBackgroundColor(Color.BLUE);
		BarDataset datasetWeather = new BarDataset().setData(getWeatherData()).setLabel("Wettereinträge Zuwachs")
				.setBorderColor(Color.RED).setBorderWidth(2).setBackgroundColor(Color.RED);
		BarDataset datasetLine = new BarDataset().setData(getLineData()).setLabel("Linien Zuwachs")
				.setBorderColor(Color.GREEN).setBorderWidth(2).setBackgroundColor(Color.GREEN);

		BarData data = new BarData().addDataset(datasetStop).addDataset(datasetWeather).addDataset(datasetLine).addLabels(getLabels());

		BarOptions options = new BarOptions().setResponsive(true);

		return new BarChart(data, options).toJson();
	}

	private List<BigDecimal> getLineData()
	{
		return Data.getCountLinesDeltas().stream().map(e -> BigDecimal.valueOf(e.getValue().getValue()))
				.collect(Collectors.toList());
	}

	private List<BigDecimal> getStopData()
	{
		// BigDecimal[] test = {BigDecimal.valueOf(8000000),
		// BigDecimal.valueOf(8500000), BigDecimal.valueOf(9500000)};
		// return Arrays.asList(test);
		return Data.getCountStopsDeltas().stream().map(e -> BigDecimal.valueOf(e.getValue().getValue()))
				.collect(Collectors.toList());
	}

	private List<BigDecimal> getWeatherData()
	{
		// BigDecimal[] test = {BigDecimal.valueOf(3000000),
		// BigDecimal.valueOf(4000000), BigDecimal.valueOf(5000000)};
		// return Arrays.asList(test);
		return Data.getCountWeatersDeltas().stream().map(e -> BigDecimal.valueOf(e.getValue().getValue()))
				.collect(Collectors.toList());
	}

	private String[] getLabels()
	{
		String[] test = { "vor 5 Minuten", "vor 10 Minuten", "vor 15 Minuten", "vor 20 Minuten", "vor 25 Minuten",
				"vor 30 Minuten", "vor 35 Minuten", "vor 40 Minuten", "vor 45 Minuten", "vor 50 Minuten" };
		return test;
	}
}
