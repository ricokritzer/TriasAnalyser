package de.dhbw.studienarbeit.WebView.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;

@StyleSheet("https://unpkg.com/leaflet@1.4.0/dist/leaflet.css")
@JavaScript("https://unpkg.com/leaflet@1.4.0/dist/leaflet.js")
public class MapDiv extends Div
{
	private static final long serialVersionUID = 1L;

	public MapDiv()
	{
		setId("map");
		setSizeFull();
	}

	public void initMap()
	{
		getElement().setAttribute("id", "map");
		getElement().getStyle().set("height", "90vh");
		getUI().orElse(UI.getCurrent()).getPage().executeJavaScript("setTimeout(initMap, 10000)\r\n"
				+ "function initMap() {"
				+ "var mymap = L.map('map').setView([49.0095795577620000, 8.4045749173222800], 13);\r\n" + 
				"		L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {\r\n" + 
				"		attribution: 'Map data &copy; <a href=\"https://www.openstreetmap.org/\">OpenStreetMap</a> contributors, <a href=\"https://creativecommons.org/licenses/by-sa/2.0/\">CC-BY-SA</a>, Imagery © <a href=\"https://www.mapbox.com/\">Mapbox</a>',\r\n" + 
				"		maxZoom: 18,\r\n" + 
				"		id: 'mapbox.streets',\r\n" + 
				"		accessToken: 'pk.eyJ1IjoicGF0LXNpZSIsImEiOiJjanNoeG94NGkwYzcxNDRxZXQ0end0ZDZlIn0.3ZOmhgqISDU_mCFZdMbmiQ'\r\n" + 
				"		}).addTo(mymap);\r\n" + 
				"		\r\n" + 
				"		var marker_Marktplatz = L.marker([49.0095795577620000, 8.4045749173222800]).addTo(mymap);\r\n" + 
				"		marker_Marktplatz.bindPopup(\"Karlsruhe Marktplatz\");\r\n" + 
				"		\r\n" + 
				"		var marker_Herrenstrasse = L.marker([49.0097916817274000, 8.4000563914425300]).addTo(mymap);\r\n" + 
				"		marker_Herrenstrasse.bindPopup(\"Karlsruhe Herrenstraße\");\r\n" + 
				"		\r\n" + 
				"		var polyline_Marktplatz_Herrenstrasse = L.polyline([\r\n" + 
				"				[49.0095795577620000 - 0.00005, 8.4045749173222800 + 0.00005],\r\n" + 
				"				[49.0097916817274000 - 0.00005, 8.4000563914425300 + 0.00005]\r\n" + 
				"			], {color: 'red'}).addTo(mymap);\r\n" + 
				"			\r\n" + 
				"		var polyline_Herrenstrasse_Marktplatz = L.polyline([\r\n" + 
				"				[49.0097916817274000 + 0.00005, 8.4000563914425300 - 0.00005],\r\n" + 
				"				[49.0095795577620000 + 0.00005, 8.4045749173222800 - 0.00005]\r\n" + 
				"			], {color: 'green'}).addTo(mymap);}");
	}
}
