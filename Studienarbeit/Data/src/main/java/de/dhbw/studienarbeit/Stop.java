package de.dhbw.studienarbeit;

public class Stop
{
	private String estimatedTime = "";
	private String publishedLineName = "";
	private String destinationText = "";

	public Stop(String estimatedTime, String publishedLineName, String destinationText)
	{
		super();
		this.estimatedTime = estimatedTime;
		this.publishedLineName = publishedLineName;
		this.destinationText = destinationText;
	}

	public String toString()
	{
		return publishedLineName + "\t\t" + destinationText + "\t\t" + estimatedTime;
	}
}
