package de.dhbw.studienarbeit.WebView.charts;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import be.ceau.chart.LineChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.LineData;
import be.ceau.chart.dataset.LineDataset;
import be.ceau.chart.options.LineOptions;
import de.dhbw.studienarbeit.web.data.Data;

public class StopAndWeatherCountChart extends AbstractChart
{
	private static final long serialVersionUID = 1L;

	protected String getChart()
	{
		LineDataset datasetStop = new LineDataset().setData(getStopData()).setLabel("Stops").setBorderColor(Color.BLUE).setBorderWidth(2).setBackgroundColor(Color.TRANSPARENT);
		LineDataset datasetWeather = new LineDataset().setData(getWeatherData()).setLabel("Wettereinträge").setBorderColor(Color.RED).setBorderWidth(2).setBackgroundColor(Color.TRANSPARENT);
		
		LineData data = new LineData().addDataset(datasetStop).addDataset(datasetWeather).addLabels(getLabels());
		
		LineOptions options = new LineOptions().setResponsive(true);
		
		return new LineChart(data, options).toJson();
	}

	private List<BigDecimal> getWeatherData()
	{
//		BigDecimal[] test = {BigDecimal.valueOf(3000000), BigDecimal.valueOf(4000000), BigDecimal.valueOf(5000000)};
//		return Arrays.asList(test);
		return Data.getCountsWO().getData().stream().map(e -> BigDecimal.valueOf(e.getCountWeathers().getValue())).collect(Collectors.toList());
	}

	private String[] getLabels()
	{
//		String[] test = {"hallo", "123", "test"};
//		return test;
		return Data.getCountsWO().getData().stream().map(e -> new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(e.getLastUpdate())).toArray(String[]::new);
	}

	private List<BigDecimal> getStopData()
	{
//		BigDecimal[] test = {BigDecimal.valueOf(8000000), BigDecimal.valueOf(8500000), BigDecimal.valueOf(9500000)};
//		return Arrays.asList(test);
		return Data.getCountsWO().getData().stream().map(e -> BigDecimal.valueOf(e.getCountStops().getValue())).collect(Collectors.toList());
	}
}
