package de.dhbw.studienarbeit.WebView.util;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;

class DisplayHelperTest
{

	@Test
	void sekundenWerdenRichtigKonvertiert()
	{
		assertThat(DisplayHelper.delayToString(11.111), is("11.11s"));
	}
	
	@Test
	void sekundenWerdenRichtigAufgerundet()
	{
		assertThat(DisplayHelper.delayToString(11.115), is("11.12s"));
	}
	
	@Test
	void MinutenWerdenRichtigKonvertiert()
	{
		assertThat(DisplayHelper.delayToString(65.123), is("1m 5s"));
	}
	
	@Test
	void StundenWerdenRichtigKonvertiert()
	{
		assertThat(DisplayHelper.delayToString(9000), is("2h 30m 0s"));
	}
}
