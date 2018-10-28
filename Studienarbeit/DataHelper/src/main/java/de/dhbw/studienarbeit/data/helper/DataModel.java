package de.dhbw.studienarbeit.data.helper;

import java.io.IOException;
import java.util.Date;

public interface DataModel
{
	String getSQLQuerry();

	void updateData(final ApiKey apiKey) throws IOException;

	Date nextUpdate();
}
