package de.dhbw.studienarbeit.WebView.tiles;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.dom.ElementFactory;

import de.dhbw.studienarbeit.data.reader.data.weather.DelayWeatherCorrelationData;

public class CorrelationCoefficientView<T extends DelayWeatherCorrelationData> extends Div
{
	private static final long serialVersionUID = 1L;

	public CorrelationCoefficientView(String what, T correlation)
	{
		setSizeFull();
		H1 text = new H1();
		text.add("Korrelationskoeffizient für");
		text.add(what);
		text.add(":");
		text.getElement().appendChild(ElementFactory.createBr());
		text.add(correlation.toString());
		text.getStyle().set("text-align", "center");
		add(text);
	}

}
