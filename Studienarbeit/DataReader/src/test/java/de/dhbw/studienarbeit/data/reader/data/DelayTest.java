package de.dhbw.studienarbeit.data.reader.data;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class DelayTest
{
	@Test
	public void sekundenWerdenRichtigKonvertiert()
	{
		assertThat(new Delay(11.111).toString(), is("11,11sek"));
	}

	@Test
	public void sekundenWerdenRichtigAufgerundet()
	{
		assertThat(new Delay(11.115).toString(), is("11,12sek"));
	}

	@Test
	public void MinutenWerdenRichtigKonvertiert()
	{
		assertThat(new Delay(65.123).toString(), is("1min 5sek"));
	}

	@Test
	public void StundenWerdenRichtigKonvertiert()
	{
		assertThat(new Delay(9000).toString(), is("2h 30min 0sek"));
	}

	@Test
	public void OneMinute()
	{
		assertThat(new Delay(60).toString(), is("1min 0sek"));
	}
	
	@Test
	public void NegativNull()
	{
		assertThat(new Delay(0).toString(), is("0,00sek"));
	}
	
	@Test
	public void NegativSeconds()
	{
		assertThat(new Delay(-1).toString(), is("-1,00sek"));
	}
	
	@Test
	public void NegativMinute()
	{
		assertThat(new Delay(-60).toString(), is("-1min 0sek"));
	}
}
