package de.dhbw.studienarbeit.data.helper;

import java.io.IOException;

public interface DataModel
{
	String getSQLQuerry();

	void updateData(final int attempts) throws IOException;
}
