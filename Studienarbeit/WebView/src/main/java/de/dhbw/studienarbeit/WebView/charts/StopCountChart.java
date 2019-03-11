package de.dhbw.studienarbeit.WebView.charts;

import org.apache.commons.lang3.StringUtils;

import com.syndybat.chartjs.ChartJs;
import com.vaadin.flow.component.html.Div;

import be.ceau.chart.BarChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.options.BarOptions;
import be.ceau.chart.options.Legend;
import be.ceau.chart.options.Title;

public class StopCountChart extends Div
{
	private static final long serialVersionUID = 1L;

	public StopCountChart()
	{
		ChartJs chart = new ChartJs(getChart());
		add(chart);
		setSizeFull();
	}

	private String getChart()
	{
		BarDataset dataset = new BarDataset().setLabel("sample chart").setData(65, 59, 80, 81, 56, 55, 40)
				.addBackgroundColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.GRAY,
						Color.BLACK)
				.setBorderWidth(2);

		BarData data = new BarData()
				.addLabels("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
				.addDataset(dataset);

		BarOptions barOptions = new BarOptions().setResponsive(true).setTitle(new Title().setText("test"))
				.setLegend(new Legend().setDisplay(true));

		System.out.printf(new BarChart(data, barOptions).toJson());
		System.out.printf(StringUtils.deleteWhitespace(new BarChart(data, barOptions).toJson()));
		return new BarChart(data, barOptions).toJson();
	}
}
