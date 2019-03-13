package de.dhbw.studienarbeit.data.reader.data.situation;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.reader.data.DelayAverage;
import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class DelaySituationDB implements DelaySituation
{
	private static final Logger LOGGER = Logger.getLogger(DelaySituationDB.class.getName());

	@Override
	public List<DelaySituationData> getDelays() throws IOException
	{
		final List<DelaySituationData> delays = new ArrayList<>();
		final List<SituationData> situations = new SituationDB().getSituations();

		situations.forEach(s -> getDelay(s).ifPresent(delays::add));

		return delays;
	}

	private static final Optional<DelaySituationDataPart> getDelay(ResultSet result)
	{
		try
		{
			final DelayAverage delayAverage = new DelayAverage(result.getDouble("delay_avg"));
			final boolean value = result.getBoolean("withSituation");

			return Optional.of(new DelaySituationDataPart(delayAverage, value));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to " + DelaySituationData.class.getName(), e);
			return Optional.empty();
		}
	}

	public final List<DelaySituationDataPart> getDelayParts(SituationID situationID) throws IOException
	{
		final String sql = "SELECT avg(UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timetabledTime)) AS delay_avg, (stopID IN (SELECT stopID FROM StopSituation WHERE situationID = ?)) AS withSituation "
				+ "FROM Stop WHERE lineID IN (SELECT DISTINCT lineID FROM StopSituation, Stop WHERE Stop.stopID = StopSituation.stopID AND StopSituation.situationID = ?) "
				+ "GROUP BY withSituation;";
		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			preparedStatement.setString(1, situationID.getValue());
			preparedStatement.setString(2, situationID.getValue());

			final List<DelaySituationDataPart> list = new ArrayList<>();
			database.select(r -> getDelay(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}

	private Optional<DelaySituationData> getDelay(SituationData data)
	{
		try
		{
			final List<DelaySituationDataPart> parts = getDelayParts(data.getSituationID());

			if (parts.size() < 2)
			{
				return Optional.empty();
			}

			DelayAverage withSituations;
			DelayAverage withoutSituations;

			if (parts.get(0).isWithSituation())
			{
				withSituations = parts.get(0).getDelay();
				withoutSituations = parts.get(1).getDelay();
			}
			else
			{
				withSituations = parts.get(1).getDelay();
				withoutSituations = parts.get(0).getDelay();
			}

			return Optional.of(new DelaySituationData(data.getSituationName(), withSituations, withoutSituations));
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Unable to get DelaySituationParts.", e);
			return Optional.empty();
		}
	}
}
