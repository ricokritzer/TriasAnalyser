package de.dhbw.studienarbeit.web.data.situation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.data.situation.DelaySituation;
import de.dhbw.studienarbeit.data.reader.data.situation.DelaySituationDB;
import de.dhbw.studienarbeit.data.reader.data.situation.DelaySituationData;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;
import de.dhbw.studienarbeit.web.data.update.Updateable;

public class DelaySituationWO extends Updateable
{
	private DelaySituation delaySituation = new DelaySituationDB();

	private List<DelaySituationData> data = new ArrayList<>();

	public DelaySituationWO(Optional<DataUpdater> updater)
	{
		updater.ifPresent(u -> u.updateEvery(1, DAYS, this));
	}

	public List<DelaySituationData> getData()
	{
		return data;
	}

	@Override
	protected void updateData() throws IOException
	{
		data = delaySituation.getDelays();
	}

	public void setDelyaSituation(DelaySituation delaySituation)
	{
		this.delaySituation = delaySituation;
		update();
	}
}
