package de.dhbw.studienarbeit.WebView.charts;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import com.syndybat.chartjs.ChartJs;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import be.ceau.chart.LineChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.LineData;
import be.ceau.chart.dataset.LineDataset;
import be.ceau.chart.options.LineOptions;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.web.data.counts.CountWO;

public class CountLineChart extends Div
{
	private static final long serialVersionUID = 1L;
	private List<CountWO> countWO;
	private String name;

	public CountLineChart(CountData current, List<CountWO> countWO, String name, String zusatz)
	{
		this.countWO = countWO;
		this.name = name;
		ChartJs chart = new ChartJs(getChart());
		Div divChart = new Div(chart);
		divChart.setVisible(false);
		VerticalLayout layoutText = new VerticalLayout(new H1(name), new H1(current.toString()), new Text(zusatz));
		layoutText.setAlignItems(Alignment.CENTER);
		Div divText = new Div(layoutText);
		add(divText, divChart);

		divChart.addClickListener(event -> {
			divChart.setVisible(false);
			divText.setVisible(true);
		});
		
		divText.addClickListener(event -> {
			divText.setVisible(false);
			divChart.setVisible(true);
		});

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
		return countWO.stream().map(e -> BigDecimal.valueOf(e.getValue().getValue())).collect(Collectors.toList());
	}

	private String[] getLabels()
	{
		return countWO.stream().map(e -> new SimpleDateFormat("HH:mm").format(e.getLastUpdate()))
				.toArray(String[]::new);
	}
}
