package de.dhbw.studienarbeit.data.helper.datamanagement;

public class UpdateException extends Exception
{
	private static final long serialVersionUID = 1L;

	public UpdateException(final String message)
	{
		super(message);
	}

	public UpdateException(final String message, final Throwable cause)
	{
		super(message, cause);
	}
}
