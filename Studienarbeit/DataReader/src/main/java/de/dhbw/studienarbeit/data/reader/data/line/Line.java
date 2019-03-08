package de.dhbw.studienarbeit.data.reader.data.line;

public class Line implements LineData
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
	public String getLineName()
	{
		return lineName.getValue();
	}

	@Override
	public String getLineDestination()
	{
		return lineDestination.getValue();
	}
}
