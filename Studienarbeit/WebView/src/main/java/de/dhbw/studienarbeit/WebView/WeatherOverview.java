package de.dhbw.studienarbeit.WebView;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

import de.dhbw.studienarbeit.WebView.components.DelayCloudsDiv;
import de.dhbw.studienarbeit.WebView.components.DelayHumidityDiv;
import de.dhbw.studienarbeit.WebView.components.DelayPressureDiv;
import de.dhbw.studienarbeit.WebView.components.DelayTemperatureDiv;
import de.dhbw.studienarbeit.WebView.components.DelayWeatherDiv;
import de.dhbw.studienarbeit.WebView.components.DelayWindDiv;

@Route("weather")
public class WeatherOverview extends Overview
{
	private static final long serialVersionUID = 1L;

	public WeatherOverview()
	{
		super();

		Component content = new Div(new DelayWeatherDiv(), new DelayTemperatureDiv(), new DelayCloudsDiv(),
				new DelayHumidityDiv(), new DelayPressureDiv(), new DelayWindDiv());
		setContent(content);
	}
}
