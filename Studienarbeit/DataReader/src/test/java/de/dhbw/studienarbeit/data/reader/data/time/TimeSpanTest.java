package de.dhbw.studienarbeit.data.reader.data.time;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.Test;

public class TimeSpanTest
{
	@Test
	public void testOrder() throws Exception
	{
		final List<TimeSpan> hoursBackwards = new ArrayList<>();
		Arrays.asList(TimeSpan.values()).forEach(h -> hoursBackwards.add(0, h));

		Collections.sort(hoursBackwards);

		assertThat(hoursBackwards.get(0), Is.is(TimeSpan.BETWEEN_00_AND_01));
		assertThat(hoursBackwards.get(1), Is.is(TimeSpan.BETWEEN_01_AND_02));
		assertThat(hoursBackwards.get(2), Is.is(TimeSpan.BETWEEN_02_AND_03));
		assertThat(hoursBackwards.get(3), Is.is(TimeSpan.BETWEEN_03_AND_04));
		assertThat(hoursBackwards.get(4), Is.is(TimeSpan.BETWEEN_04_AND_05));
		assertThat(hoursBackwards.get(5), Is.is(TimeSpan.BETWEEN_05_AND_06));
		assertThat(hoursBackwards.get(6), Is.is(TimeSpan.BETWEEN_06_AND_07));
		assertThat(hoursBackwards.get(7), Is.is(TimeSpan.BETWEEN_07_AND_08));
		assertThat(hoursBackwards.get(8), Is.is(TimeSpan.BETWEEN_08_AND_09));
		assertThat(hoursBackwards.get(9), Is.is(TimeSpan.BETWEEN_09_AND_10));
		assertThat(hoursBackwards.get(10), Is.is(TimeSpan.BETWEEN_10_AND_11));
		assertThat(hoursBackwards.get(11), Is.is(TimeSpan.BETWEEN_11_AND_12));
		assertThat(hoursBackwards.get(12), Is.is(TimeSpan.BETWEEN_12_AND_13));
		assertThat(hoursBackwards.get(13), Is.is(TimeSpan.BETWEEN_13_AND_14));
		assertThat(hoursBackwards.get(14), Is.is(TimeSpan.BETWEEN_14_AND_15));
		assertThat(hoursBackwards.get(15), Is.is(TimeSpan.BETWEEN_15_AND_16));
		assertThat(hoursBackwards.get(16), Is.is(TimeSpan.BETWEEN_16_AND_17));
		assertThat(hoursBackwards.get(17), Is.is(TimeSpan.BETWEEN_17_AND_18));
		assertThat(hoursBackwards.get(18), Is.is(TimeSpan.BETWEEN_18_AND_19));
		assertThat(hoursBackwards.get(19), Is.is(TimeSpan.BETWEEN_19_AND_20));
		assertThat(hoursBackwards.get(20), Is.is(TimeSpan.BETWEEN_20_AND_21));
		assertThat(hoursBackwards.get(21), Is.is(TimeSpan.BETWEEN_21_AND_22));
		assertThat(hoursBackwards.get(22), Is.is(TimeSpan.BETWEEN_22_AND_23));
		assertThat(hoursBackwards.get(23), Is.is(TimeSpan.BETWEEN_23_AND_00));
	}
}
