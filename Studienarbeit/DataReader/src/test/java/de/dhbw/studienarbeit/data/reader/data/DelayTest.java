package de.dhbw.studienarbeit.data.reader.data;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class DelayTest
{
	@Test
	public void sekundenWerdenRichtigKonvertiert()
	{
		assertThat(new Delay(11.111).toString(), is("11,11s"));
	}

	@Test
	public void sekundenWerdenRichtigAufgerundet()
	{
		assertThat(new Delay(11.115).toString(), is("11,12s"));
	}

	@Test
	public void MinutenWerdenRichtigKonvertiert()
	{
		assertThat(new Delay(65.123).toString(), is("1m 5s"));
	}

	@Test
	public void StundenWerdenRichtigKonvertiert()
	{
		assertThat(new Delay(9000).toString(), is("2h 30m 0s"));
	}

	@Test
	public void OneMinute()
	{
		assertThat(new Delay(60).toString(), is("1m 0s"));
	}
	
	@Test
	public void NegativNull()
	{
		assertThat(new Delay(0).toString(), is("0,00s"));
	}
	
	@Test
	public void NegativSeconds()
	{
		assertThat(new Delay(-1).toString(), is("-1,00s"));
	}
	
	@Test
	public void NegativMinute()
	{
		assertThat(new Delay(-60).toString(), is("-1m 0s"));
	}
}
