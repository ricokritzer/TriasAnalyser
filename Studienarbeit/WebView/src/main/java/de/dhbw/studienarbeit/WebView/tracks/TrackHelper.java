package de.dhbw.studienarbeit.WebView.tracks;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.database.StationNeighbourDB;
import edu.emory.mathcs.backport.java.util.Collections;

public class TrackHelper
{
	protected static final Color COLOR_MAXIMUM = Color.RED;
	protected static final Color COLOR_MEDIUM = Color.YELLOW;
	protected static final Color COLOR_MINIMUM = Color.GREEN;
	protected static final Color COLOR_NEGATIVE = Color.BLUE;

	private TrackHelper()
	{}

	public static List<Track> convertToTracks(List<StationNeighbourDB> stationNeighbours)
	{
		if (stationNeighbours.isEmpty())
		{
			return new ArrayList<>();
		}

		final List<Track> tracks = new ArrayList<>();
		Collections.sort(tracks);

		final double maximum = tracks.get(tracks.size() - 1).getSlope();

		stationNeighbours.forEach(sn -> tracks.add(new Track(sn.getLat1(), sn.getLon1(), sn.getAvg1(), sn.getLat2(),
				sn.getLon2(), sn.getAvg2(), getColorFor(sn.getSlope(), maximum))));
		return tracks;
	}

	protected static Color getColorFor(double slope, double maxSlope)
	{
		if (slope < 0)
		{
			return COLOR_NEGATIVE;
		}
		if (slope >= maxSlope)
		{
			return COLOR_MAXIMUM;
		}
		if (maxSlope <= 0.0)
		{
			return COLOR_NEGATIVE;
		}

		double slopePercentage = slope / maxSlope;

		if (slopePercentage > 0.5)
		{
			final int red = (int) (COLOR_MAXIMUM.getRed() * slopePercentage
					+ COLOR_MEDIUM.getRed() * (1 - slopePercentage));
			final int green = (int) (COLOR_MAXIMUM.getGreen() * slopePercentage
					+ COLOR_MEDIUM.getGreen() * (1 - slopePercentage));
			final int blue = (int) (COLOR_MAXIMUM.getBlue() * slopePercentage
					+ COLOR_MEDIUM.getBlue() * (1 - slopePercentage));

			return new Color(red, green, blue);
		}

		slopePercentage = slopePercentage * 2;
		final int red = (int) (COLOR_MEDIUM.getRed() * slopePercentage
				+ COLOR_MINIMUM.getRed() * (1 - slopePercentage));
		final int green = (int) (COLOR_MEDIUM.getGreen() * slopePercentage
				+ COLOR_MINIMUM.getGreen() * (1 - slopePercentage));
		final int blue = (int) (COLOR_MEDIUM.getBlue() * slopePercentage
				+ COLOR_MINIMUM.getBlue() * (1 - slopePercentage));

		return new Color(red, green, blue);
	}
}
