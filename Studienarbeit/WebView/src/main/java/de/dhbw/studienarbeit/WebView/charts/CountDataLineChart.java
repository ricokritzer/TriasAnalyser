package de.dhbw.studienarbeit.WebView.charts;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.syndybat.chartjs.ChartJs;
import com.vaadin.flow.component.html.Div;

import be.ceau.chart.LineChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.LineData;
import be.ceau.chart.dataset.LineDataset;
import be.ceau.chart.options.LineOptions;
import de.dhbw.studienarbeit.data.reader.data.CancelledStopsData;

public class CountDataLineChart<T> extends Div
{
	private static final long serialVersionUID = 1L;
	private List<CancelledStopsData<T>> countData;
	private String name;

	public CountDataLineChart(List<CancelledStopsData<T>> countData, String name)
	{
		this.countData = countData;
		this.name = name;
		ChartJs chart = new ChartJs(getChart());
		add(chart);

		setSizeFull();
	}

	protected String getChart()
	{
		LineDataset dataset = new LineDataset().setData(getData()).setLabel(name).setBorderColor(Color.BLUE)
				.setBorderWidth(2).setBackgroundColor(Color.TRANSPARENT);

		LineData data = new LineData().addDataset(dataset).addLabels(getLabels());

		LineOptions options = new LineOptions().setResponsive(true);

		return new LineChart(data, options).toJson();
	}

	private List<BigDecimal> getData()
	{
		return countData.stream().map(e -> BigDecimal.valueOf(e.getCount().getValue())).collect(Collectors.toList());
	}

	private String[] getLabels()
	{
		return countData.stream().map(e -> e.getValue().toString()).toArray(String[]::new);
	}
}
