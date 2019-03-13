package de.dhbw.studienarbeit.web.data.counts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.dhbw.studienarbeit.data.reader.data.count.Count;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public abstract class CountListWO<T extends Count> extends Updateable
{
	protected static final int MAX_COUNT_ITEMS = 10;

	protected List<CountWO> values = new ArrayList<>();

	protected T counter;

	protected void add(CountWO countWO)
	{
		values.add(0, countWO);
		reduceListElements(values);
	}

	public void setCounter(T counter)
	{
		this.counter = counter;
		update();
	}

	@Override
	protected void updateData() throws IOException
	{
		final CountData value = counter.count();
		final Date lastUpdate = new Date();
		add(new CountWO(value, lastUpdate));
	}

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

	public CountWO getNewestValue()
	{
		if (values.isEmpty())
		{
			return new CountWO(CountData.UNABLE_TO_COUNT, new Date(0));
		}
		return values.get(0);
	}
}
