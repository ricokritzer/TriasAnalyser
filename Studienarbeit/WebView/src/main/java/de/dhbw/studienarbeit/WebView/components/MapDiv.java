package de.dhbw.studienarbeit.WebView.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;

import de.dhbw.studienarbeit.WebView.util.MapGenerator;

@StyleSheet("https://unpkg.com/leaflet@1.4.0/dist/leaflet.css")
@JavaScript("https://unpkg.com/leaflet@1.4.0/dist/leaflet.js")
public class MapDiv extends Div
{
	private static final long serialVersionUID = 3L;

	public MapDiv()
	{
		setId("map");
		setSizeFull();
	}

	public void initMap()
	{
		MapGenerator generator = new MapGenerator();
		getElement().setAttribute("id", "map");
		getElement().getStyle().set("height", "90vh");
		getUI().orElse(UI.getCurrent()).getPage().executeJavaScript("initMap()"
				+ System.lineSeparator() + "function initMap() {" + generator.generate() + "}");
	}
}
