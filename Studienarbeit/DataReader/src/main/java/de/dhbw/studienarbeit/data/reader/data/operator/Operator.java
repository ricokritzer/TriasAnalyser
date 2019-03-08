package de.dhbw.studienarbeit.data.reader.data.operator;

import java.io.IOException;
import java.util.List;

public interface Operator
{
	List<OperatorID> getAllOperators() throws IOException;

	List<OperatorID> getObservedOperators() throws IOException;
}
