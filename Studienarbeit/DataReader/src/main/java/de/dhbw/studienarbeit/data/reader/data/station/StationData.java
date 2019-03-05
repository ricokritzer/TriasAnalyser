package de.dhbw.studienarbeit.data.reader.data.station;

public interface StationData
{
	@Deprecated
	String getStationName();

	StationName getName();

	@Deprecated
	double getLat();

	@Deprecated
	double getLon();

	Position getPosition();

	String getOperator();
}
