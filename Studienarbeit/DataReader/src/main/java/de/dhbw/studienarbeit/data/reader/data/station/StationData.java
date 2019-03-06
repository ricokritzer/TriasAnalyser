package de.dhbw.studienarbeit.data.reader.data.station;

import de.dhbw.studienarbeit.data.reader.database.Operator;

public interface StationData
{
	StationName getName();

	Position getPosition();

	Operator getOperator();
}
