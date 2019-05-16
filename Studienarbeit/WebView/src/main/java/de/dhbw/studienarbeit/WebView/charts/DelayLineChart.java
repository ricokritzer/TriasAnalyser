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

public class DelayLineChart extends Div
{
	private static final long serialVersionUID = 1L;
	private List<? extends DelayData<? extends Object>> delayWO;
	private String name;
	private LineData data;

	public DelayLineChart(List<? extends DelayData<? extends Object>> delayList, String name)
	{
		this.delayWO = delayList;
		this.name = name;
		data = new LineData().addLabels(getLabels());
	}

	public DelayLineChart create()
	{
		add(new ChartJs(getChart()));
		setSizeFull();
		return this;
	}

	private String getChart()
	{

		LineOptions options = new LineOptions().setResponsive(true);

		return new LineChart(data, options).toJson();
	}

	public DelayLineChart withAvgData()
	{
		LineDataset datasetAvg = new LineDataset().setData(getAvgData()).setLabel(name + " - Durchschnitt (in Sek.)")
				.setBorderColor(Color.BLUE).setBorderWidth(2).setBackgroundColor(Color.TRANSPARENT);
		data.addDataset(datasetAvg);
		return this;
	}
	
	public DelayLineChart withMaxData()
	{
		LineDataset datasetMax = new LineDataset().setData(getMaxData()).setLabel(name + " - Maximum (in Sek.)")
				.setBorderColor(Color.BLUE).setBorderWidth(2).setBackgroundColor(Color.TRANSPARENT);
		data.addDataset(datasetMax);
		return this;
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
		return delayWO.stream().map(e -> e.getValueString()).toArray(String[]::new);
	}
}
