package de.dhbw.studienarbeit.WebView.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;

import de.dhbw.studienarbeit.web.data.Data;

public class CorrelationCoefficientsDiv extends Div
{
	private static final long serialVersionUID = 12L;

	public CorrelationCoefficientsDiv()
	{
		super();

		VerticalLayout layout = new VerticalLayout();

		final TextArea txt = new TextArea();
		txt.setLabel("Korrelationskoeffizienten");
		txt.setReadOnly(true);
		txt.setValue("Bewölkung: " + Data.getDelayCloudsCorrelationCoefficient() + System.lineSeparator()
				+ "Luftfeuchtigkeit: " + Data.getDelayHumidityCorrelationCoefficient() + System.lineSeparator()
				+ "Luftdruck: " + Data.getDelayPressureCorrelationCoefficient() + System.lineSeparator()
				+ "Windstärke: " + Data.getDelayWindCorrelationCoefficient() + System.lineSeparator() + "Temperatur: "
				+ Data.getDelayTemperatureCorrelationCoefficient());

		layout.add(txt);
		add(layout);
	}
}
