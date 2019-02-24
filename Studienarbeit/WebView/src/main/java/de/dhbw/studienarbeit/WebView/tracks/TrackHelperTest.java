package de.dhbw.studienarbeit.WebView.tracks;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TrackHelperTest
{
	private static final double MAXIMUM_SLOPE = 100.0;
	private static final double NEGATIVE_SLOPE = -1.0;
	private static final double RANDOM_SLOPE = 42.0;

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
