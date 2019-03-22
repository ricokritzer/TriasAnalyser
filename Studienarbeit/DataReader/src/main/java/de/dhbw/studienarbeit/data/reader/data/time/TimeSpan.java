package de.dhbw.studienarbeit.data.reader.data.time;

import java.util.Arrays;

public enum TimeSpan
{
	BETWEEN_00_AND_01("00:00 - 00:59 Uhr"), //
	BETWEEN_01_AND_02("01:00 - 01:59 Uhr"), //
	BETWEEN_02_AND_03("02:00 - 02:59 Uhr"), //
	BETWEEN_03_AND_04("03:00 - 03:59 Uhr"), //
	BETWEEN_04_AND_05("04:00 - 04:59 Uhr"), //
	BETWEEN_05_AND_06("05:00 - 05:59 Uhr"), //
	BETWEEN_06_AND_07("06:00 - 06:59 Uhr"), //
	BETWEEN_07_AND_08("07:00 - 07:59 Uhr"), //
	BETWEEN_08_AND_09("08:00 - 08:59 Uhr"), //
	BETWEEN_09_AND_10("09:00 - 09:59 Uhr"), //
	BETWEEN_10_AND_11("10:00 - 10:59 Uhr"), //
	BETWEEN_11_AND_12("11:00 - 11:59 Uhr"), //
	BETWEEN_12_AND_13("12:00 - 12:59 Uhr"), //
	BETWEEN_13_AND_14("13:00 - 13:59 Uhr"), //
	BETWEEN_14_AND_15("14:00 - 14:59 Uhr"), //
	BETWEEN_15_AND_16("15:00 - 15:59 Uhr"), //
	BETWEEN_16_AND_17("16:00 - 16:59 Uhr"), //
	BETWEEN_17_AND_18("17:00 - 17:59 Uhr"), //
	BETWEEN_18_AND_19("18:00 - 18:59 Uhr"), //
	BETWEEN_19_AND_20("19:00 - 19:59 Uhr"), //
	BETWEEN_20_AND_21("20:00 - 20:59 Uhr"), //
	BETWEEN_21_AND_22("21:00 - 21:59 Uhr"), //
	BETWEEN_22_AND_23("22:00 - 22:59 Uhr"), //
	BETWEEN_23_AND_00("23:00 - 23:59 Uhr");

	private final String text;

	private TimeSpan(String text)
	{
		this.text = text;
	}

	public String getText()
	{
		return text;
	}

	public int getIdx()
	{
		return Arrays.asList(TimeSpan.values()).indexOf(this);
	}
}
