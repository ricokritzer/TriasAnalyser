package de.dhbw.studienarbeit.web.data;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.Test;

public class CountListTest
{
	@Test
	public void reduceToTenDoesNothingByHavingLessElements() throws Exception
	{
		final List<Integer> list = getListWithElements(10);

		CountList.reduceToTen(list);

		assertThat(list.size(), Is.is(10));
	}

	@Test
	public void reduceToTenReducesIfThereAre11Elements() throws Exception
	{
		final List<Integer> list = getListWithElements(11);

		CountList.reduceToTen(list);

		assertThat(list.size(), Is.is(10));
	}

	@Test
	public void reduceToTenReducesIfThereAre12Elements() throws Exception
	{
		final List<Integer> list = getListWithElements(12);

		CountList.reduceToTen(list);

		assertThat(list.size(), Is.is(10));
	}

	private List<Integer> getListWithElements(int count)
	{
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 10; i++)
		{
			list.add(Integer.valueOf(i));
		}
		return list;
	}
}
