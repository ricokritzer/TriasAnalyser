package de.dhbw.studienarbeit.data.reader.data.station;

public interface DelayStationNeighbourData
{
	StationName getName1();

	@Deprecated
	double getLat1();

	@Deprecated
	double getLon1();

	Position getPosition1();

	double getAvg1();

	StationName getName2();

	@Deprecated
	double getLat2();

	@Deprecated
	double getLon2();

	Position getPosition2();

	double getAvg2();

	double getSlope();
}
