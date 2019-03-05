package de.dhbw.studienarbeit.data.reader.data.station;

import de.dhbw.studienarbeit.data.reader.database.Operator;

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

	Operator getOperator();
}
