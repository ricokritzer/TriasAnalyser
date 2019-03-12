package de.dhbw.studienarbeit.web.data.counts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.count.Count;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public abstract class CountListWO extends Updateable
{
	protected static final int MAX_COUNT_ITEMS = 10;

	protected List<CountWO> values = new ArrayList<>();

	protected void add(CountWO countWO)
	{
		values.add(0, countWO);
		reduceListElements(values);
	}

	@Override
	protected void updateData() throws IOException
	{
		final Count value = count();
		final Date lastUpdate = new Date();
		add(new CountWO(value, lastUpdate));
	}

	protected abstract Count count() throws IOException;

	public final List<CountWO> getValues()
	{
		return values;
	}

	protected static void reduceListElements(List<? extends Object> list)
	{
		while (list.size() > MAX_COUNT_ITEMS)
		{
			list.remove(MAX_COUNT_ITEMS);
		}
	}
}
