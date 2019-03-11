package de.dhbw.studienarbeit.data.reader.data.time;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class WeekdayTest
{
	@Test
	public void mondayIs0() throws Exception
	{
		assertThat(Weekday.MONDAY.getIdx(), is(0));
	}

	@Test
	public void tuesdayIs1() throws Exception
	{
		assertThat(Weekday.TUESDAY.getIdx(), is(1));
	}

	@Test
	public void wednesdayIs2() throws Exception
	{
		assertThat(Weekday.WEDNESDAY.getIdx(), is(2));
	}

	@Test
	public void thursdayIs3() throws Exception
	{
		assertThat(Weekday.THURSDAY.getIdx(), is(3));
	}

	@Test
	public void fridayIs4() throws Exception
	{
		assertThat(Weekday.FRIDAY.getIdx(), is(4));
	}

	@Test
	public void saturdayIs5() throws Exception
	{
		assertThat(Weekday.SATURDAY.getIdx(), is(5));
	}

	@Test
	public void sundayIs6() throws Exception
	{
		assertThat(Weekday.SUNDAY.getIdx(), is(6));
	}

	@Test
	public void mondayBeforeTuesday()
	{
		final List<Weekday> days = new ArrayList<>();
		days.add(Weekday.TUESDAY);
		days.add(Weekday.MONDAY);

		Collections.sort(days);

		assertThat(days.get(0), is(Weekday.MONDAY));
		assertThat(days.get(1), is(Weekday.TUESDAY));
	}

	@Test
	public void tuesdayBeforeWednesday()
	{
		final List<Weekday> days = new ArrayList<>();
		days.add(Weekday.WEDNESDAY);
		days.add(Weekday.TUESDAY);

		Collections.sort(days);

		assertThat(days.get(0), is(Weekday.TUESDAY));
		assertThat(days.get(1), is(Weekday.WEDNESDAY));
	}

	@Test
	public void wednesdayBeforeThursday()
	{
		final List<Weekday> days = new ArrayList<>();
		days.add(Weekday.THURSDAY);
		days.add(Weekday.WEDNESDAY);

		Collections.sort(days);

		assertThat(days.get(0), is(Weekday.WEDNESDAY));
		assertThat(days.get(1), is(Weekday.THURSDAY));
	}

	@Test
	public void thursdayBeforeFriday()
	{
		final List<Weekday> days = new ArrayList<>();
		days.add(Weekday.FRIDAY);
		days.add(Weekday.THURSDAY);

		Collections.sort(days);

		assertThat(days.get(0), is(Weekday.THURSDAY));
		assertThat(days.get(1), is(Weekday.FRIDAY));
	}

	@Test
	public void fridayBeforeSaturday()
	{
		final List<Weekday> days = new ArrayList<>();
		days.add(Weekday.SATURDAY);
		days.add(Weekday.FRIDAY);

		Collections.sort(days);

		assertThat(days.get(0), is(Weekday.FRIDAY));
		assertThat(days.get(1), is(Weekday.SATURDAY));
	}

	@Test
	public void saturdayBeforeSunday()
	{
		final List<Weekday> days = new ArrayList<>();
		days.add(Weekday.SUNDAY);
		days.add(Weekday.SATURDAY);

		Collections.sort(days);

		assertThat(days.get(0), is(Weekday.SATURDAY));
		assertThat(days.get(1), is(Weekday.SUNDAY));
	}

	@Test
	public void justReorderingMakesTestFail()
	{
		final List<Weekday> days = new ArrayList<>();
		days.add(Weekday.SATURDAY);
		days.add(Weekday.SUNDAY);

		Collections.sort(days);

		assertThat(days.get(0), not(Weekday.SUNDAY));
		assertThat(days.get(1), not(Weekday.SATURDAY));
	}
}
