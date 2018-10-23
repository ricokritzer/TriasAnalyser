package de.dhbw.studienarbeit.data.helper;

public class UnableToSaveException extends Exception
{
	private static final long serialVersionUID = 1L;

	public UnableToSaveException(final String message, final Throwable cause)
	{
		super(message, cause);
	}
}
