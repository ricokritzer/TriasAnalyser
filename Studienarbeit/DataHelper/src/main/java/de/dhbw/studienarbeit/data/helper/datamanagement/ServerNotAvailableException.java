package de.dhbw.studienarbeit.data.helper.datamanagement;

public class ServerNotAvailableException extends Exception
{
	private static final long serialVersionUID = 1L;

	public ServerNotAvailableException(final String message)
	{
		super(message);
	}
}
