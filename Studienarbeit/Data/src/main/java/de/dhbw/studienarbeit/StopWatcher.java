package de.dhbw.studienarbeit;

import java.util.ArrayList;
import java.util.List;

public class StopWatcher
{
	private final List<String> stopIDsWithResponse = new ArrayList<>();
	private final List<String> stopIDsWithoutResponse = new ArrayList<>();

	private int idx = 1;
	private static final String STOP_ID_PRE = "de:8212:";

	public String createNewStopID()
	{
		final String stopID = STOP_ID_PRE + idx;
		idx++;
		return stopID;
	}

	public void addToListWithResponse(String stopID)
	{
		stopIDsWithResponse.add(stopID);
	}

	public void addToListWithoutResponse(String stopID)
	{
		stopIDsWithoutResponse.add(stopID);
	}

	public int countStopIDsWithResponse()
	{
		return stopIDsWithResponse.size();
	}

	public int countStopIDsWithoutResponse()
	{
		return stopIDsWithoutResponse.size();
	}
}
