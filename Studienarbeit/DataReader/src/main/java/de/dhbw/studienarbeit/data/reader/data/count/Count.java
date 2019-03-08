package de.dhbw.studienarbeit.data.reader.data.count;

public interface Count
{
	public static final Count UNABLE_TO_COUNT = () -> -1;

	long getValue();
}
