package de.dhbw.studienarbeit.data.reader.data.line;

public class Line
{
	private final LineID lineID;
	private final LineName lineName;
	private final LineDestination lineDestination;

	public Line(LineID lineID, LineName lineName, LineDestination lineDestination)
	{
		super();
		this.lineID = lineID;
		this.lineName = lineName;
		this.lineDestination = lineDestination;
	}

	public LineID getID()
	{
		return lineID;
	}

	public LineName getName()
	{
		return lineName;
	}

	public LineDestination getDestination()
	{
		return lineDestination;
	}

	@Override
	public String toString()
	{
		return new StringBuilder(lineName.toString()).append(" -> ").append(lineDestination.toString()).toString();
	}
}
