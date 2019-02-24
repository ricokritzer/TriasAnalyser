package de.dhbw.studienarbeit.WebView.tracks;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.database.StationNeighbourDB;

public class TrackHelper
{
	private TrackHelper()
	{}

	public static List<Track> convertToTracks(List<StationNeighbourDB> stationNeighbours)
	{
		List<Track> tracks = new ArrayList<>();
		stationNeighbours.forEach(sn -> tracks.add(new Track(sn.getLat1(), sn.getLon1(), sn.getAvg1(), sn.getLat2(),
				sn.getLon2(), sn.getAvg2(), Color.BLUE)));
		return tracks;
	}
}
