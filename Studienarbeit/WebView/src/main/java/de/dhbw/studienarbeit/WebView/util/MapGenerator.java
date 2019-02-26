package de.dhbw.studienarbeit.WebView.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.database.DelayStationDB;
import de.dhbw.studienarbeit.data.reader.database.StationDB;
import de.dhbw.studienarbeit.data.reader.database.StationNeighbourDB;
import de.dhbw.studienarbeit.web.data.Data;

public class MapGenerator
{
	private static final double distanceNorthSouth = 0.0005;
	private static final double distanceEastWest = 0.0005;

	StringBuilder sb;

	public MapGenerator()
	{
		sb = new StringBuilder();
	}

	public String generate()
	{
		sb.append("var mymap = L.map('map').setView([49.0095795577620000, 8.4045749173222800], 13);")
				.append(System.lineSeparator()) //
				.append("L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {attribution: 'Map data &copy; <a href=\"https://www.openstreetmap.org/\">OpenStreetMap</a> contributors, <a href=\"https://creativecommons.org/licenses/by-sa/2.0/\">CC-BY-SA</a>, Imagery © <a href=\"https://www.mapbox.com/\">Mapbox</a>', maxZoom: 18, id: 'mapbox.streets', accessToken: 'pk.eyJ1IjoicGF0LXNpZSIsImEiOiJjanNoeG94NGkwYzcxNDRxZXQ0end0ZDZlIn0.3ZOmhgqISDU_mCFZdMbmiQ'}).addTo(mymap);")
				.append(System.lineSeparator());
		getMarkers();
		sb.append(System.lineSeparator());
		getTracks();
		return sb.toString();
	}

	private void getTracks()
	{
		sb.append("var polyline_Marktplatz_Herrenstrasse = L.polyline([[49.0095795577620000 - 0.00005, 8.4045749173222800 + 0.00005],[49.0097916817274000 - 0.00005, 8.4000563914425300 + 0.00005]], {color: 'red'}).addTo(mymap);") //
		.append(System.lineSeparator()) //
		.append("var polyline_Herrenstrasse_Marktplatz = L.polyline([[49.0097916817274000 + 0.00005, 8.4000563914425300 - 0.00005],[49.0095795577620000 + 0.00005, 8.4045749173222800 - 0.00005]], {color: 'green'}).addTo(mymap);");
	}

	private void getMarkers()
	{
		int i = 0;
		for (DelayStationDB station : getStations())
		{
			sb.append("var marker_" + i + " = L.marker([" + station.getLat() + "," + station.getLon() + "]).addTo(mymap);") //
			.append(System.lineSeparator()) //
			.append("marker_" + i + ".bindPopup('" + station.getStationName() + "');") //
			.append(System.lineSeparator());
			i++;
		}
	}

	private List<DelayStationDB> getStations()
	{
		List<DelayStationDB> stations = new ArrayList<>();
		
		stations.add(new DelayStationDB(0, 0, "Karlsruhe Marktplatz", "kvv", 49.0095795577620000, 8.4045749173222800, 1));
		stations.add(new DelayStationDB(0, 0, "Karlsruhe Herrenstraße", "kvv", 49.0097916817274000, 8.4000563914425300, 1));
		
		return stations;
		
		//return Data.getDelaysStation();
	}
}
