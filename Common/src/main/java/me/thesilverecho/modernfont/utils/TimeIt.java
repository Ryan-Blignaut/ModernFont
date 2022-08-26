package me.thesilverecho.modernfont.utils;

public class TimeIt
{
	public static void timeIt(Runnable runnable)
	{
		final long startTime = System.nanoTime();
		runnable.run();
		final long endTime = System.nanoTime();
		final long duration = (endTime - startTime) / 1000000;
		FontLog.LogMessage("Time taken: " + duration + "ms");
	}


}
