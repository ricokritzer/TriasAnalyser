package de.dhbw.studienarbeit.data.reader.data.station;

public interface StationNeighbourData
{
	String getStationID1();

	@Deprecated
	String getStationName1();

	StationName getName1();

	@Deprecated
	double getLat1();

	@Deprecated
	double getLon1();

	Position getPosition1();

	String getStationID2();

	@Deprecated
	String getStationName2();

	StationName getName2();

	@Deprecated
	double getLat2();

	@Deprecated
	double getLon2();

	Position getPosition2();
}
