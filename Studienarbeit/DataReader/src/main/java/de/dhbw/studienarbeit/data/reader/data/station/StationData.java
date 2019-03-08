package de.dhbw.studienarbeit.data.reader.data.station;

public interface StationData
{
	StationID getStationID();

	StationName getName();

	Position getPosition();

	OperatorName getOperator();
}
