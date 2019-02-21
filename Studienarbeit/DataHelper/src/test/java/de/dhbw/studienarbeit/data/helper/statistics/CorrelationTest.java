package de.dhbw.studienarbeit.data.helper.statistics;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.Is;
import org.junit.Test;

public class CorrelationTest
{
	@Test
	public void correlateLinearData() throws Exception
	{
		List<Correlatable> list = new ArrayList<>();
		list.add(getData(1, 1));
		list.add(getData(2, 2));
		list.add(getData(3, 3));
		list.add(getData(4, 4));

		double correate = Correlation.of(list);

		assertThat(correate, Is.is(1.0));
	}

	@Test
	public void correlateLinearDataNegative() throws Exception
	{
		List<Correlatable> list = new ArrayList<>();
		list.add(getData(1, 4));
		list.add(getData(2, 3));
		list.add(getData(3, 2));
		list.add(getData(4, 1));

		double correate = Correlation.of(list);

		assertThat(correate, Is.is(-1.0));
	}

	@Test
	public void correlateNull() throws Exception
	{
		List<Correlatable> list = new ArrayList<>();
		list.add(getData(1, 2));
		list.add(getData(2, 1));
		list.add(getData(3, 1));
		list.add(getData(4, 2));

		double correate = Correlation.of(list);

		assertThat(correate, Is.is(0.0));
	}

	private Correlatable getData(double x, double y)
	{
		return new Correlatable()
		{
			@Override
			public double getX()
			{
				return x;
			}

			@Override
			public double getY()
			{
				return y;
			}
		};
	}
}
