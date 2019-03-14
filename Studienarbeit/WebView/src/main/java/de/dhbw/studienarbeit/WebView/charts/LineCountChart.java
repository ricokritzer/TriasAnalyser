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

public class LineCountChart extends AbstractChart
{
	private static final long serialVersionUID = 1L;

	@Override
	protected String getChart()
	{
		LineDataset dataset = new LineDataset().setData(getData()).setLabel("Linien").setBorderColor(Color.BLUE).setBorderWidth(2).setBackgroundColor(Color.TRANSPARENT);
		
		LineData data = new LineData().addDataset(dataset).addLabels(getLabels());
		
		LineOptions options = new LineOptions().setResponsive(true);
		
		return new LineChart(data, options).toJson();
	}

	private List<BigDecimal> getData()
	{
		return Data.getCountsWO().getData().stream().map(e -> BigDecimal.valueOf(e.getCountLines().getValue())).collect(Collectors.toList());
	}
	
	private String[] getLabels()
	{
		return Data.getCountsWO().getData().stream().map(e -> new SimpleDateFormat("HH:mm:ss").format(e.getLastUpdate())).toArray(String[]::new);
	}
}
