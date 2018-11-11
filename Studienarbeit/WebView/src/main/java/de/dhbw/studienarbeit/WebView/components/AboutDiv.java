package de.dhbw.studienarbeit.WebView.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;

public class AboutDiv extends Div
{
	private static final long serialVersionUID = 1L;

	public AboutDiv()
	{
		add(new Label("Unsere Studienarbeit"));
		add(new Label("Patrick Siewert"));
		add(new Label("Rico Kritzer"));
		add(new Label());
		add(new Label("Impressum:"));
		add(new Label("Duale Hochschule Baden-Württemberg Karlsruhe"));
		add(new Label("Erzbergerstraße 121"));
		add(new Label("76133 Karlsruhe"));
		add(new Label("Deutschland"));
	}
}
