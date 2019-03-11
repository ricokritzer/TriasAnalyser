package de.dhbw.studienarbeit.WebView.components;

import org.apache.commons.lang3.StringUtils;

import com.syndybat.chartjs.ChartJs;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextArea;

import be.ceau.chart.BarChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.options.BarOptions;
import be.ceau.chart.options.Legend;
import be.ceau.chart.options.Title;

public class WelcomeDiv extends Div
{
	private static final long serialVersionUID = 1L;

	public WelcomeDiv()
	{
		super();

		final TextArea txtAbout = new TextArea();
		txtAbout.setLabel("Unsere Studienarbeit");
		txtAbout.setReadOnly(true);
		txtAbout.setValue("Patrick Siewert" + System.lineSeparator() + "Rico Kritzer");
		txtAbout.setSizeUndefined();
		add(txtAbout);

		final TextArea txtImpress = new TextArea();
		txtImpress.setLabel("Impressum");
		txtImpress.setReadOnly(true);
		txtImpress.setValue(new StringBuilder() //
				.append("Duale Hochschule").append(System.lineSeparator()) //
				.append("Erzbergerstraße 121").append(System.lineSeparator()) //
				.append("76133 Karlsruhe").append(System.lineSeparator()) //
				.append("Deutschland") //
				.toString());
		txtImpress.setSizeUndefined();
		add(txtImpress);

		ChartJs barChartJs = new ChartJs(getBarChart());
		Div div2 = new Div();
		div2.add(barChartJs);
		div2.setSizeUndefined();
		add(div2);

	}

	private String getBarChart()
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
