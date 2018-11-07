package de.dhbw.studienarbeit.data.helper.logging;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogLevelHelper
{
	public static void setLogLevel(Level level)
	{
		final Logger rootLogger = LogManager.getLogManager().getLogger("");
		rootLogger.setLevel(level);
		Arrays.asList(rootLogger.getHandlers()).forEach(h -> h.setLevel(level));
	}
}
