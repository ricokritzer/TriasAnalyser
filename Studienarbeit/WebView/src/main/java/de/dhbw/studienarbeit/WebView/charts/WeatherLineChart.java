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
import de.dhbw.studienarbeit.data.reader.data.DelayData;

public class WeatherLineChart extends Div
{
	private static final long serialVersionUID = 1L;
	private List<? extends DelayData<Double>> delayWO;
	private String name;

	public WeatherLineChart(List<? extends DelayData<Double>> delayWO, String name)
	{
		this.delayWO = delayWO;
		this.name = name;
		ChartJs chart = new ChartJs(getChart());
		add(chart);

		setSizeFull();
	}

	protected String getChart()
	{
		LineDataset datasetAvg = new LineDataset().setData(getAvgData()).setLabel(name + " Durchschnitt")
				.setBorderColor(Color.BLUE).setBorderWidth(2).setBackgroundColor(Color.TRANSPARENT);

		LineDataset datasetMax = new LineDataset().setData(getMaxData()).setLabel(name + " Maximum")
				.setBorderColor(Color.RED).setBorderWidth(2).setBackgroundColor(Color.TRANSPARENT);

		LineData data = new LineData().addDataset(datasetAvg).addDataset(datasetMax).addLabels(getLabels());

		LineOptions options = new LineOptions().setResponsive(true);

		return new LineChart(data, options).toJson();
	}

	private List<BigDecimal> getAvgData()
	{
		return delayWO.stream().map(e -> BigDecimal.valueOf(e.getAverage().getValue())).collect(Collectors.toList());
	}

	private List<BigDecimal> getMaxData()
	{
		return delayWO.stream().map(e -> BigDecimal.valueOf(e.getMaximum().getValue())).collect(Collectors.toList());
	}

	private String[] getLabels()
	{
		return delayWO.stream().map(e -> e.getValue().toString()).toArray(String[]::new);
	}
}
