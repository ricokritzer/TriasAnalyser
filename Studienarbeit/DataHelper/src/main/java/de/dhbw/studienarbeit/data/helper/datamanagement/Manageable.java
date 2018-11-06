package de.dhbw.studienarbeit.data.helper.datamanagement;

import java.io.IOException;
import java.util.Date;

public interface Manageable
{
	void updateAndSaveData(final ApiKey apiKey) throws IOException;

	Date nextUpdate();
}
