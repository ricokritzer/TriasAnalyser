package de.dhbw.studienarbeit.data.helper.datamanagement;

import java.io.IOException;
import java.util.Date;

import de.dhbw.studienarbeit.data.helper.database.saver.DataSaverModel;

public interface DataModel extends DataSaverModel
{
	void updateData(final ApiKey apiKey) throws IOException;

	Date nextUpdate();
}
