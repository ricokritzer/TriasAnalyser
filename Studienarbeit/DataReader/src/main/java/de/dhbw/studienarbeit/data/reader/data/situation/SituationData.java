package de.dhbw.studienarbeit.data.reader.data.situation;

public class SituationData
{
	private final SituationName situationName;
	private final SituationID situationID;

	public SituationData(SituationName situationName, SituationID situationID)
	{
		super();
		this.situationName = situationName;
		this.situationID = situationID;
	}

	public SituationName getSituationName()
	{
		return situationName;
	}

	public SituationID getSituationID()
	{
		return situationID;
	}
}
