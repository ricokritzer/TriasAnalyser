package de.dhbw.studienarbeit;

public class Stop
{
	private String timetabledTime = "";
	private String estimatedTime = "";
	private String publishedLineName = "";
	private String destinationText = "";

	public Stop(String timetabledTime, String estimatedTime, String publishedLineName, String destinationText)
	{
		super();
		this.timetabledTime = timetabledTime;
		this.estimatedTime = estimatedTime;
		this.publishedLineName = publishedLineName.substring(0, publishedLineName.length() - 2);
		this.destinationText = destinationText.substring(0, destinationText.length() - 2);
	}

	public String toString()
	{
		return "Linie " + publishedLineName + "\t" + destinationText + "\tgeplant: " + timetabledTime
				+ "\tvorraussichtlich: " + estimatedTime;
	}
}
