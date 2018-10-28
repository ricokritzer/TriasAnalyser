package de.dhbw.studienarbeit.data.helper;

import java.io.IOException;
import java.util.Date;

public interface DataModel
{
	String getSQLQuerry();

	@Deprecated
	void updateData(final int attempts) throws IOException;

	//this function should be implemented. It'll be used soon.
	// void updateData(final ApiKey apiKey) throws IOException;

	Date nextUpdate();
}
