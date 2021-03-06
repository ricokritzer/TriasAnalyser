package de.dhbw.studienarbeit.WebView.overview;

import java.util.Iterator;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.Route;

import de.dhbw.studienarbeit.WebView.charts.CountDataLineChart;
import de.dhbw.studienarbeit.WebView.charts.DelayLineChart;
import de.dhbw.studienarbeit.WebView.tiles.CorrelationCoefficientView;
import de.dhbw.studienarbeit.WebView.tiles.Tile;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.weather.clouds.Clouds;
import de.dhbw.studienarbeit.data.reader.data.weather.humidity.Humidity;
import de.dhbw.studienarbeit.data.reader.data.weather.pressure.Pressure;
import de.dhbw.studienarbeit.data.reader.data.weather.symbol.WeatherSymbol;
import de.dhbw.studienarbeit.data.reader.data.weather.temperature.Temperature;
import de.dhbw.studienarbeit.data.reader.data.weather.wind.Wind;
import de.dhbw.studienarbeit.web.data.Data;

@Route("weather")
public class WeatherOverview extends Overview
{
	private static final long serialVersionUID = 1L;

	public WeatherOverview()
	{
		H4 text = new H4();

		showWeatherSymbolDelays(text);

		text.getStyle().set("text-align", "center");
		Div txtWeather = new Div(text);
		txtWeather.setSizeFull();

		HorizontalLayout row1 = new HorizontalLayout(txtWeather,
				new Tile(getTemperatureChart().withAvgData().create(), getTemperatureChart().withMaxData().create(),
						new CountDataLineChart<Temperature>(Data.getCancelledStopsTemperature(), "Temperatur Ausfälle in %"),
						new CorrelationCoefficientView("Temperatur", Data.getDelayTemperatureCorrelationCoefficient())),
				new Tile(getPressureChart().withAvgData().create(), getPressureChart().withMaxData().create(),
						new CountDataLineChart<Pressure>(Data.getCancelledStopsPressure(), "Luftdruck Ausfälle in %"),
						new CorrelationCoefficientView("Luftdruck", Data.getDelayPressureCorrelationCoefficient())));
		row1.setSizeFull();
		row1.setAlignItems(Alignment.CENTER);

		HorizontalLayout row2 = new HorizontalLayout(
				new Tile(getCloudsChart().withAvgData().create(), getCloudsChart().withMaxData().create(),
						new CountDataLineChart<Clouds>(Data.getCancelledStopsClouds(), "Bewölkung Ausfälle in %"),
						new CorrelationCoefficientView("Bewölkung", Data.getDelayCloudsCorrelationCoefficient())),
				new Tile(getHumidityChart().withAvgData().create(), getHumidityChart().withMaxData().create(),
						new CountDataLineChart<Humidity>(Data.getCancelledStopsHumidity(), "Luftfeuchtigkeit Ausfälle in %"),
						new CorrelationCoefficientView("Luftfeuchtigkeit",
								Data.getDelayHumidityCorrelationCoefficient())),
				new Tile(getWindChart().withAvgData().create(), getWindChart().withMaxData().create(),
						new CountDataLineChart<Wind>(Data.getCancelledStopsWind(), "Windstärke Ausfälle in %"),
						new CorrelationCoefficientView("Wind", Data.getDelayWindCorrelationCoefficient())));
		row2.setSizeFull();
		row2.setAlignItems(Alignment.CENTER);

		VerticalLayout content = new VerticalLayout(row1, row2);
		content.setSizeFull();

		setContent(content);
	}

	private void showWeatherSymbolDelays(H4 text)
	{
		Iterator<DelayData<WeatherSymbol>> iterator = Data.getDelaysWeatherSymbols().iterator();
		while (iterator.hasNext())
		{
			addLeftColumn(text, iterator);

			if (iterator.hasNext())
			{
				addRightColumn(text, iterator);

				if (iterator.hasNext())
				{
					text.getElement().appendChild(ElementFactory.createBr());
				}
			}
		}
	}

	private void addRightColumn(H4 text, Iterator<DelayData<WeatherSymbol>> iterator)
	{
		DelayData<WeatherSymbol> symbolRight = iterator.next();
		Image imageRight = new Image(symbolRight.getValue().getURLString(), symbolRight.getValue().getName());
		text.add(" ");
		text.add(imageRight);
		text.add(symbolRight.getAverage().toString());
	}

	private void addLeftColumn(H4 text, Iterator<DelayData<WeatherSymbol>> iterator)
	{
		DelayData<WeatherSymbol> symbol = iterator.next();
		Image imageLeft = new Image(symbol.getValue().getURLString(), symbol.getValue().getName());
		text.add(imageLeft);
		text.add(symbol.getAverage().toString());
	}

	private DelayLineChart getWindChart()
	{
		return new DelayLineChart(Data.getDelaysWind(), "Windstärke");
	}

	private DelayLineChart getHumidityChart()
	{
		return new DelayLineChart(Data.getDelaysHumidity(), "Luftfeuchtigkeit");
	}

	private DelayLineChart getCloudsChart()
	{
		return new DelayLineChart(Data.getDelaysClouds(), "Bewölkung");
	}

	private DelayLineChart getPressureChart()
	{
		return new DelayLineChart(Data.getDelaysPressure(), "Luftdruck");
	}

	private DelayLineChart getTemperatureChart()
	{
		return new DelayLineChart(Data.getDelaysTemperature(), "Temperatur");
	}
}
