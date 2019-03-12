package de.dhbw.studienarbeit.web.data.counts;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import de.dhbw.studienarbeit.data.reader.data.count.Count;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;

public class CountsListWOTest
{
	@Test
	public void reduceToTenDoesNothingByHavingLessElements() throws Exception
	{
		final List<Integer> list = getListWithElements(10);

		CountListWO.reduceListElements(list);

		assertThat(list.size(), is(10));
	}

	@Test
	public void reduceToTenReducesIfThereAre11Elements() throws Exception
	{
		final List<Integer> list = getListWithElements(11);

		CountListWO.reduceListElements(list);

		assertThat(list.size(), is(10));
	}

	@Test
	public void reduceToTenReducesIfThereAre12Elements() throws Exception
	{
		final List<Integer> list = getListWithElements(12);

		CountListWO.reduceListElements(list);

		assertThat(list.size(), is(10));
	}

	@Test
	public void testWillRemoveOldestElement() throws Exception
	{
		final CountListWO<Count> countListWO = createCountListWO();

		final CountWO oldestCount = createCountWO();
		countListWO.add(oldestCount);

		addElements(CountListWO.MAX_COUNT_ITEMS, countListWO);

		final CountWO newestCount = createCountWO();
		countListWO.add(newestCount);

		assertTrue(countListWO.values.contains(newestCount));
		assertFalse(countListWO.values.contains(oldestCount));
	}

	@Test
	public void addingToMuchElementsWillReduceToMaximum() throws Exception
	{
		final CountListWO<Count> countListWO = createCountListWO();

		addElements(CountListWO.MAX_COUNT_ITEMS + 1, countListWO);

		assertThat(countListWO.values.size(), is(CountListWO.MAX_COUNT_ITEMS));
	}

	private CountListWO<Count> createCountListWO()
	{
		return new CountListWO<Count>()
		{};
	}

	private CountWO createCountWO()
	{
		return new CountWO(new CountData(1), new Date());
	}

	private void addElements(int count, CountListWO<Count> countListWO)
	{
		for (int i = 0; i < count; i++)
		{
			countListWO.add(createCountWO());
		}
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
