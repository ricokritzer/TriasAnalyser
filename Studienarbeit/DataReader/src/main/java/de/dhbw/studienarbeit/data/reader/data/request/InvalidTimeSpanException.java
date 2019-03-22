package de.dhbw.studienarbeit.data.reader.data.request;

import java.io.IOException;

public class InvalidTimeSpanException extends IOException
{
	private static final long serialVersionUID = 1L;

	public InvalidTimeSpanException()
	{
		super("Endtime has to be before starttime");
	}
}
