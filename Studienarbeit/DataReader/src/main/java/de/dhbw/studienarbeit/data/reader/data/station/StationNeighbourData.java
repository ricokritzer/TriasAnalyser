package de.dhbw.studienarbeit.data.reader.data.station;

public interface StationNeighbourData
{
	StationNeighbourPart getStationFrom();

	StationNeighbourPart getStationTo();

	@Deprecated
	String getStationID1();

	@Deprecated
	String getStationName1();

	@Deprecated
	StationName getName1();

	@Deprecated
	double getLat1();

	@Deprecated
	double getLon1();

	@Deprecated
	Position getPosition1();

	@Deprecated
	String getStationID2();

	@Deprecated
	String getStationName2();

	@Deprecated
	StationName getName2();

	@Deprecated
	double getLat2();

	@Deprecated
	double getLon2();

	@Deprecated
	Position getPosition2();
}
