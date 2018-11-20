package de.dhbw.studienarbeit.data.helper.datamanagement;

import java.util.Date;

public interface Manageable
{
	void updateAndSaveData(final ApiKey apiKey) throws TimeOutException, ServerNotAvailableException;

	Date nextUpdate();
}
