package de.dhbw.studienarbeit.WebView.util;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;

import de.dhbw.studienarbeit.data.reader.data.Delay;

class DisplayHelperTest
{

	@Test
	void sekundenWerdenRichtigKonvertiert()
	{
		assertThat(new Delay(11.111).toString(), is("11.11s"));
	}
	
	@Test
	void sekundenWerdenRichtigAufgerundet()
	{
		assertThat(new Delay(11.115).toString(), is("11.12s"));
	}
	
	@Test
	void MinutenWerdenRichtigKonvertiert()
	{
		assertThat(new Delay(65.123).toString(), is("1m 5s"));
	}
	
	@Test
	void StundenWerdenRichtigKonvertiert()
	{
		assertThat(new Delay(9000).toString(), is("2h 30m 0s"));
	}
}
