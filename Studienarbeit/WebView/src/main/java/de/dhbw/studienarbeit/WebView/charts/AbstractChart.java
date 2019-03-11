package de.dhbw.studienarbeit.WebView.charts;

import com.syndybat.chartjs.ChartJs;
import com.vaadin.flow.component.html.Div;

public abstract class AbstractChart extends Div
{
	private static final long serialVersionUID = 1L;

	public AbstractChart()
	{
		ChartJs chart = new ChartJs(getChart());
		add(chart);
		setSizeFull();
	}
	
	protected abstract String getChart();
}
