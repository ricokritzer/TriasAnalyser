package de.dhbw.studienarbeit.web.data.counts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import de.dhbw.studienarbeit.data.reader.data.count.Count;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public abstract class CountListWO<T extends Count> extends Updateable
{
	protected static final int MAX_COUNT_ITEMS = 10;

	protected Queue<CountWO> values = new LinkedBlockingQueue<>();

	protected Queue<CountWO> deltas = new LinkedBlockingQueue<>();

	protected CountData lastAdded = CountData.UNABLE_TO_COUNT;

	protected T counter;

	protected void add(CountWO countWO)
	{
		values.add(countWO);
		reduceElements(values);

		deltas.add(new CountWO(lastAdded.difference(countWO.getValue()), countWO.getLastUpdate()));
		reduceElements(deltas);

		lastAdded = countWO.getValue();
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
		return asList(values);
	}

	public final List<CountWO> getDeltas()
	{
		return asList(deltas);
	}

	private final List<CountWO> asList(Queue<? extends CountWO> queue)
	{
		return new ArrayList<>(queue);
	}

	protected static void reduceElements(Queue<? extends Object> queue)
	{
		while (queue.size() > MAX_COUNT_ITEMS)
		{
			queue.remove();
		}
	}

	public CountData getNewestValue()
	{
		return lastAdded;
	}
}
