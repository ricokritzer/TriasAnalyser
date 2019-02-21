package de.dhbw.studienarbeit.web.data;

import java.util.Date;

public interface Updateable
{
	void update();

	Date lastUpdated();
}
