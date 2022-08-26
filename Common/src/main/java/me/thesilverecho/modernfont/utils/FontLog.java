package me.thesilverecho.modernfont.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FontLog
{
	private static final Logger LOGGER = LogManager.getLogger("ModernFont");

	public static void LogMessage(String message)
	{
		LOGGER.info(message);
	}

	public static void LogDebug(String message)
	{
		LOGGER.debug(message);
	}

	public static void LogError(String s)
	{
		LOGGER.error(s);
	}
}
