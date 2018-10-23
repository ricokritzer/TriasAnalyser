package de.dhbw.studienarbeit.data.helper;

public interface Saver
{
	public void logError(Exception ex);

	public void save(DataModel model) throws UnableToSaveException;
}
