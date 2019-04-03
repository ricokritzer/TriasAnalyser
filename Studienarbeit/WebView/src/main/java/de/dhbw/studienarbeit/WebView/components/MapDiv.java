package de.dhbw.studienarbeit.WebView.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

import de.dhbw.studienarbeit.WebView.map.MapGenerator;
import de.dhbw.studienarbeit.WebView.overview.Overview;

@Route("map")
@StyleSheet("https://unpkg.com/leaflet@1.4.0/dist/leaflet.css")
@JavaScript("https://unpkg.com/leaflet@1.4.0/dist/leaflet.js")
public class MapDiv extends Overview
{
	private static final long serialVersionUID = 3L;
	private boolean init = false;
	private Div mapDiv = new Div();

	public MapDiv()
	{
		super();
		mapDiv.setId("mapComplete");
		mapDiv.setSizeFull();
		
		setContent(mapDiv);
		initMap();
	}

	public void initMap()
	{
		if (init)
		{
			return;
		}
		
		getUI().orElse(UI.getCurrent()).getPage().executeJavaScript(
				"initMap()" + System.lineSeparator() + "function initMap() {" + MapGenerator.generate("mapComplete").withMarkers().withTracks().get() + "}");
		init = true;
	}
}
