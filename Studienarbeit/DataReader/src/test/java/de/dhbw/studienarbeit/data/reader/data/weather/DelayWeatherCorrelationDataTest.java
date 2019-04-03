package de.dhbw.studienarbeit.data.reader.data.weather;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class DelayWeatherCorrelationDataTest
{
	@Test
	public void toString0() throws Exception
	{
		assertThat(getStringOf(0), is("0,00"));
	}

	@Test
	public void toStringSmallValue() throws Exception
	{
		assertThat(getStringOf(0.004), is("0,00"));
	}

	@Test
	public void toStringRoundsValue() throws Exception
	{
		assertThat(getStringOf(0.005), is("0,01"));
	}

	@Test
	public void toStringNegativValue() throws Exception
	{
		assertThat(getStringOf(-1), is("-1,00"));
	}

	private String getStringOf(double value)
	{
		return createDelayWeatherCorrelationData(value).toString();
	}

	private DelayWeatherCorrelationData createDelayWeatherCorrelationData(double value)
	{
		return new DelayWeatherCorrelationData(value)
		{};
	}
}
