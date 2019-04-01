package de.dhbw.studienarbeit.WebView.tracks;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.data.station.DelayStationNeighbourData;
import de.dhbw.studienarbeit.data.reader.data.station.Position;
import de.dhbw.studienarbeit.data.reader.data.station.StationName;

public class TrackHelperTest
{
	private static final double MAXIMUM_SLOPE = 100.0;
	private static final double NEGATIVE_SLOPE = -1.0;
	private static final double RANDOM_SLOPE = 42.0;

	@Test
	public void convertStationNeighboursToTracks() throws Exception
	{
		final StationName name = new StationName("foo");
		final Position pos0 = new Position(0, 0);
		final Position pos1 = new Position(1, 1);
		final Position pos2 = new Position(2, 2);
		final Position pos4 = new Position(4, 4);

		final DelayAverage delayZero = new DelayAverage(0.0);

		final DelayAverage delayAverage1 = new DelayAverage(2);
		final DelayAverage delayAverage2 = new DelayAverage(4);
		final DelayAverage delayAverage3 = new DelayAverage(1);

		final List<DelayStationNeighbourData> stationNeighbours = new ArrayList<>();
		stationNeighbours.add(new DelayStationNeighbourData(name, pos0, delayZero, name, pos2, delayAverage1));
		stationNeighbours.add(new DelayStationNeighbourData(name, pos0, delayZero, name, pos4, delayAverage2));
		stationNeighbours.add(new DelayStationNeighbourData(name, pos0, delayZero, name, pos1, delayAverage3));

		final List<Track> tracks = TrackHelper.convertToTrackList(stationNeighbours);

		assertThat(tracks.size(), is(3));
		assertThat(tracks.get(2).getColor(), is(Color.RED));
	}

	@Test
	public void slopeNull() throws Exception
	{
		assertThat(TrackHelper.getColorFor(0, MAXIMUM_SLOPE), is(TrackHelper.COLOR_MINIMUM));
	}

	@Test
	public void slopeNegative() throws Exception
	{
		assertThat(TrackHelper.getColorFor(-1, MAXIMUM_SLOPE), is(TrackHelper.COLOR_NEGATIVE));
	}

	@Test
	public void maxSlopeNull() throws Exception
	{
		assertThat(TrackHelper.getColorFor(NEGATIVE_SLOPE, 0), is(TrackHelper.COLOR_NEGATIVE));
	}

	@Test
	public void maxSlopeLessThanSlope() throws Exception
	{
		assertThat(TrackHelper.getColorFor(RANDOM_SLOPE, RANDOM_SLOPE - 1), is(TrackHelper.COLOR_MAXIMUM));
	}

	@Test
	public void slopeHalf() throws Exception
	{
		assertThat(TrackHelper.getColorFor(RANDOM_SLOPE / 2, RANDOM_SLOPE), is(TrackHelper.COLOR_MEDIUM));
	}
}
