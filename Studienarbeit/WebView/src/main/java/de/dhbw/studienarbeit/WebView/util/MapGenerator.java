package de.dhbw.studienarbeit.WebView.util;

import java.util.List;

import de.dhbw.studienarbeit.WebView.tracks.Track;
import de.dhbw.studienarbeit.WebView.tracks.TrackHelper;
import de.dhbw.studienarbeit.data.reader.data.station.DelayStationData;
import de.dhbw.studienarbeit.web.data.Data;

public class MapGenerator
{
	private static final double distanceNorthSouth = 0.00005;
	private static final double distanceEastWest = 0.00005;

	StringBuilder sb;

	private MapGenerator()
	{
		sb = new StringBuilder();
	}
	
	public String get()
	{
		return sb.toString();
	}
	
	public static MapGenerator generate()
	{
		MapGenerator generator = new MapGenerator();
		generator.sb.append("var mymap = L.map('map').setView([49.0095795577620000, 8.4045749173222800], 13);")
				.append(System.lineSeparator()) //
				.append("L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {attribution: 'Map data &copy; <a href=\"https://www.openstreetmap.org/\">OpenStreetMap</a> contributors, <a href=\"https://creativecommons.org/licenses/by-sa/2.0/\">CC-BY-SA</a>, Imagery © <a href=\"https://www.mapbox.com/\">Mapbox</a>', maxZoom: 18, id: 'mapbox.streets', accessToken: 'pk.eyJ1IjoicGF0LXNpZSIsImEiOiJjanNoeG94NGkwYzcxNDRxZXQ0end0ZDZlIn0.3ZOmhgqISDU_mCFZdMbmiQ'}).addTo(mymap);");
		return generator;
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
		return this;
	}

	private void getTracks()
	{
		int i = 0;
		List<Track> tracks = TrackHelper.convertToTrackList(Data.getStationNeighbourWO().getData());
		for (Track track : tracks)
		{
			double lat1 = track.getDelayStationNeighbourData().getPosition1().getLat();
			double lon1 = track.getDelayStationNeighbourData().getPosition1().getLon();
			double lat2 = track.getDelayStationNeighbourData().getPosition2().getLat();
			double lon2 = track.getDelayStationNeighbourData().getPosition2().getLon();

			if (lat1 > lat2)
			{
				lon1 = lon1 - distanceEastWest;
				lon2 = lon2 - distanceEastWest;
			}
			else
			{
				lon1 = lon1 + distanceEastWest;
				lon2 = lon2 + distanceEastWest;
			}

			if (lon1 > lon2)
			{
				lat1 = lat1 - distanceNorthSouth;
				lat2 = lat2 - distanceNorthSouth;
			}
			else
			{
				lat1 = lat1 + distanceNorthSouth;
				lat2 = lat2 + distanceNorthSouth;
			}

			sb.append("var polyline_" + i + " = L.polyline([[" + lat1 + ", " + lon1 + "],[" + lat2 + ", " + lon2
					+ "]], {color: 'rgb(" + track.getColor().getRed() + "," + track.getColor().getGreen() + ","
					+ track.getColor().getBlue() + ")'}).addTo(mymap);");
			sb.append(System.lineSeparator());
			i++;
		}
	}

	private void getMarkers()
	{
		int i = 0;
		for (DelayStationData station : Data.getDelaysStationWO().getData())
		{
			sb.append("var marker_" + i + " = L.marker([" + station.getPosition().getLat() + "," + station.getPosition().getLon()
					+ "]).addTo(mymap);") //
					.append(System.lineSeparator()) //
					.append("marker_" + i + ".bindPopup('<b>" + station.getName().getStationName() + "</b><br>Durchschittliche Verspätung: " + station.getAverage().toString() + "');") //
					.append(System.lineSeparator());
			i++;
		}
	}
}
