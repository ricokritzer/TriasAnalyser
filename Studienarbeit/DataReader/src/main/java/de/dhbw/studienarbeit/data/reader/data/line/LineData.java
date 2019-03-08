package de.dhbw.studienarbeit.data.reader.data.line;

public interface LineData
{
	LineID getID();

	LineName getName();

	LineDestination getDestination();

	@Deprecated
	String getLineName();

	@Deprecated
	String getLineDestination();
}
