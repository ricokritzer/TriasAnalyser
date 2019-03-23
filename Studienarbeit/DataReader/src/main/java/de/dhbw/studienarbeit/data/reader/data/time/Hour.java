package de.dhbw.studienarbeit.data.reader.data.time;

public enum Hour
{
	HOUR1(1), //
	HOUR2(2), //
	HOUR3(3), //
	HOUR4(4), //
	HOUR5(5), //
	HOUR6(6), //
	HOUR7(7), //
	HOUR8(8), //
	HOUR9(9), //
	HOUR10(10), //
	HOUR11(11), //
	HOUR12(12), //
	HOUR13(13), //
	HOUR14(14), //
	HOUR15(15), //
	HOUR16(16), //
	HOUR17(17), //
	HOUR18(18), //
	HOUR19(19), //
	HOUR20(20), //
	HOUR21(21), //
	HOUR22(22), //
	HOUR23(23), //
	HOUR24(24), //

	;

	private final int value;

	private Hour(int value)
	{
		if (value < 0)
		{
			throw new IllegalArgumentException("Hour cannot be negative.");
		}

		if (value > 24)
		{
			throw new IllegalArgumentException("Hour cannot not be over 24.");
		}

		this.value = value;
	}

	public int getValue()
	{
		return value;
	}

	public boolean before(Hour hour)
	{
		return this.value < hour.value;
	}

	@Override
	public String toString()
	{
		return new StringBuilder().append(value).append(":00 Uhr").toString();
	}
}
