package de.dhbw.studienarbeit.data.helper.datamanagement;

import java.io.IOException;
import java.util.Date;

public interface DataModel
{
	void updateAndSaveData(final ApiKey apiKey) throws IOException;

	Date nextUpdate();
}
