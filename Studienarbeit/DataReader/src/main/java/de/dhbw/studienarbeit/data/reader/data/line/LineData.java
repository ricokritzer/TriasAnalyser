package de.dhbw.studienarbeit.data.reader.data.line;

public class LineData implements Line
{
	private final LineID lineID;
	private final LineName lineName;
	private final LineDestination lineDestination;

	public LineData(LineID lineID, LineName lineName, LineDestination lineDestination)
	{
		super();
		this.lineID = lineID;
		this.lineName = lineName;
		this.lineDestination = lineDestination;
	}

	@Override
	public LineID getID()
	{
		return lineID;
	}

	@Override
	public LineName getName()
	{
		return lineName;
	}

	@Override
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
