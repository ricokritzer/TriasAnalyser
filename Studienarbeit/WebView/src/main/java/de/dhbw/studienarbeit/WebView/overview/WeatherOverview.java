package de.dhbw.studienarbeit.WebView.overview;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.Route;

import de.dhbw.studienarbeit.WebView.charts.WeatherLineChart;
import de.dhbw.studienarbeit.WebView.tiles.Tile;
import de.dhbw.studienarbeit.data.reader.data.weather.symbol.DelayWeatherSymbolData;
import de.dhbw.studienarbeit.web.data.Data;

@Route("weather")
public class WeatherOverview extends Overview
{
	private static final long serialVersionUID = 1L;

	public WeatherOverview()
	{
		H1 text = new H1();
		for (DelayWeatherSymbolData symbol : Data.getDelaysWeatherSymbol().getData())
		{
			Image image = new Image(symbol.getValue().getURLString(), symbol.getValue().getName());
			text.add(image);
			text.add(symbol.getAverage().toString());
			text.getElement().appendChild(ElementFactory.createBr());
		}
		
		text.getStyle().set("text-align", "center");
		Div txtWeather = new Div(text);
		txtWeather.setSizeFull();

		HorizontalLayout row1 = new HorizontalLayout(txtWeather,
				new Tile(new WeatherLineChart(Data.getDelaysTemperature(), "Temperatur")),
				new Tile(new WeatherLineChart(Data.getDelaysPressure(), "Luftdruck")));
		row1.setSizeFull();
		row1.setAlignItems(Alignment.CENTER);

		HorizontalLayout row2 = new HorizontalLayout(
				new Tile(new WeatherLineChart(Data.getDelaysClouds(), "Bewölkung")),
				new Tile(new WeatherLineChart(Data.getDelaysHumidity(), "Luftfeuchtigkeit")),
				new Tile(new WeatherLineChart(Data.getDelaysWind(), "Windstärke")));
		row2.setSizeFull();
		row2.setAlignItems(Alignment.CENTER);

		VerticalLayout content = new VerticalLayout(row1, row2);
		content.setSizeFull();

		setContent(content);
	}
}
