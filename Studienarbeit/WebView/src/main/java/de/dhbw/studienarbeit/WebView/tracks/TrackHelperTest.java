package de.dhbw.studienarbeit.WebView.tracks;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.dhbw.studienarbeit.data.reader.database.DelayStationNeighbourDB;

public class TrackHelperTest
{
	private static final double MAXIMUM_SLOPE = 100.0;
	private static final double NEGATIVE_SLOPE = -1.0;
	private static final double RANDOM_SLOPE = 42.0;

	@Test
	public void convertStationNeighboursToTracks() throws Exception
	{
		final List<DelayStationNeighbourDB> stationNeighbours = new ArrayList<>();
		stationNeighbours.add(new DelayStationNeighbourDB("foo", 0, 0, 0, "bar", 2, 2, 2));
		stationNeighbours.add(new DelayStationNeighbourDB("foo", 0, 0, 0, "bar", 4, 4, 4));
		stationNeighbours.add(new DelayStationNeighbourDB("foo", 0, 0, 0, "bar", 1, 1, 1));

		final List<Track> tracks = TrackHelper.convertToTracks(stationNeighbours);

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
