package de.dhbw.studienarbeit.data.helper.database.saver;

import java.io.IOException;

public interface Saver
{
	public void save(Saveable model) throws IOException;
}
