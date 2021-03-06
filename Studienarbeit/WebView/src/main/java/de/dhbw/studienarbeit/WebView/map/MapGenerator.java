package de.dhbw.studienarbeit.WebView.map;

import java.util.List;

import de.dhbw.studienarbeit.WebView.tracks.Track;
import de.dhbw.studienarbeit.WebView.tracks.TrackHelper;
import de.dhbw.studienarbeit.data.reader.data.DelayData;
import de.dhbw.studienarbeit.data.reader.data.station.StationData;
import de.dhbw.studienarbeit.web.data.Data;

public class MapGenerator
{
	private static final double DISTANCE_NORTH_SOUTH = 0.00005;
	private static final double DISTANCE_EAST_WEST = 0.00005;

	private final StringBuilder sb;

	private MapGenerator()
	{
		sb = new StringBuilder();
	}

	public String get()
	{
		sb.append("var markersLayer = L.layerGroup(markers);");
		sb.append("var linesLayer = L.layerGroup(lines);");
		sb.append("var overlayMaps = {'Stationen': markersLayer, 'Linien': linesLayer};");
		sb.append("markersLayer.addTo(mymap); linesLayer.addTo(mymap);");
		sb.append("L.control.layers(null, overlayMaps).addTo(mymap);");
		return sb.toString();
	}

	public static MapGenerator generate(String id)
	{
		MapGenerator generator = new MapGenerator();
		generator.sb.append("var mymap = L.map('" + id + "').setView([49.0095795577620000, 8.4045749173222800], 13);")
				.append("L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {attribution: 'Map data &copy; <a href=\"https://www.openstreetmap.org/\">OpenStreetMap</a> contributors, <a href=\"https://creativecommons.org/licenses/by-sa/2.0/\">CC-BY-SA</a>, Imagery © <a href=\"https://www.mapbox.com/\">Mapbox</a>', maxZoom: 18, id: 'mapbox.streets', accessToken: 'pk.eyJ1IjoicGF0LXNpZSIsImEiOiJjanNoeG94NGkwYzcxNDRxZXQ0end0ZDZlIn0.3ZOmhgqISDU_mCFZdMbmiQ'}).addTo(mymap);");
		generator.sb.append("markers = [];").append("lines = [];");

		return generator;
	}

	public MapGenerator withLegend()
	{
		sb.append("var legend = L.control({position: 'bottomright'});").append(System.lineSeparator());
		sb.append("legend.onAdd = function (mymap) {").append(System.lineSeparator());

		sb.append(
				"var div = L.DomUtil.create('div', 'info legend'), colors = ['green', 'red', 'blue'], labels = ['geringe Verspätungszunahme', 'hohe Verspätungszunahme', 'Verspätungsabbau'];")
				.append(System.lineSeparator());
		sb.append("for (var i = 0; i < colors.length; i++) {").append(System.lineSeparator());
		sb.append("div.innerHTML += '<i style=\"background:' + colors[i] + '\"></i> ' + labels[i] + '<br>';}")
				.append(System.lineSeparator());
		sb.append("return div;").append(System.lineSeparator());

		sb.append("};").append(System.lineSeparator());
		sb.append("legend.addTo(mymap);").append(System.lineSeparator());

		return this;
	}

	public MapGenerator withMarkers()
	{
		sb.append(System.lineSeparator());
		getMarkers();
		return this;
	}

	public MapGenerator withTracks()
	{
		sb.append(System.lineSeparator());
		getTracks();
		return withLegend();
	}

	private void getTracks()
	{
		int i = 0;
		List<Track> tracks = TrackHelper.convertToTrackList(Data.getStationNeighbours());
		for (Track track : tracks)
		{
			double lat1 = track.getDelayStationNeighbourData().getPosition1().getLat();
			double lon1 = track.getDelayStationNeighbourData().getPosition1().getLon();
			double lat2 = track.getDelayStationNeighbourData().getPosition2().getLat();
			double lon2 = track.getDelayStationNeighbourData().getPosition2().getLon();

			if (lat1 > lat2)
			{
				lon1 = lon1 - DISTANCE_EAST_WEST;
				lon2 = lon2 - DISTANCE_EAST_WEST;
			}
			else
			{
				lon1 = lon1 + DISTANCE_EAST_WEST;
				lon2 = lon2 + DISTANCE_EAST_WEST;
			}

			if (lon1 > lon2)
			{
				lat1 = lat1 - DISTANCE_NORTH_SOUTH;
				lat2 = lat2 - DISTANCE_NORTH_SOUTH;
			}
			else
			{
				lat1 = lat1 + DISTANCE_NORTH_SOUTH;
				lat2 = lat2 + DISTANCE_NORTH_SOUTH;
			}

			String delayChange = track.getDelayStationNeighbourData().getDelayDifference().toString();
			sb.append("var polyline_" + i + " = L.polyline([[" + lat1 + ", " + lon1 + "],[" + lat2 + ", " + lon2
					+ "]], {color: 'rgb(" + track.getColor().getRed() + "," + track.getColor().getGreen() + ","
					+ track.getColor().getBlue() + ")'}).bindPopup('von: "
					+ track.getDelayStationNeighbourData().getName1().toString() + "<br>nach: "
					+ track.getDelayStationNeighbourData().getName2().toString() + "<br>Verspätung: " + delayChange
					+ "');");
			sb.append("lines.push(polyline_" + i + ");");
			i++;
		}
	}

	private void getMarkers()
	{
		int i = 0;
		for (DelayData<StationData> station : Data.getDelaysStation())
		{
			sb.append("var marker_" + i + " = L.marker([" + station.getValue().getPosition().getLat() + ","
					+ station.getValue().getPosition().getLon() + "]).addTo(mymap);") //
					.append(System.lineSeparator()) //
					.append("marker_" + i + ".bindPopup('<b>" + station.getValue().getName().getStationName()
							+ "</b><br>Durchschittliche Verspätung: " + station.getAverage().toString() + "');") //
					.append(System.lineSeparator());
			sb.append("markers.push(marker_" + i + ");");
			i++;
		}
	}
}
