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

import de.dhbw.studienarbeit.data.reader.database.DatabaseReader;

public class SituationDB
{
	private static final Logger LOGGER = Logger.getLogger(SituationDB.class.getName());

	private final Optional<SituationData> getSituation(ResultSet result)
	{
		try
		{
			final SituationID id = new SituationID(result.getString("situationID"));
			final SituationName name = new SituationName(result.getString("text"));

			return Optional.of(new SituationData(name, id));
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Unable to parse to stop.", e);
			return Optional.empty();
		}
	}

	public final List<SituationData> getSituations() throws IOException
	{
		final String sql = "SELECT situationID, max(version), text FROM Situation GROUP BY situationID, text;";
		final DatabaseReader database = new DatabaseReader();
		try (PreparedStatement preparedStatement = database.getPreparedStatement(sql))
		{
			final List<SituationData> list = new ArrayList<>();
			database.select(r -> getSituation(r).ifPresent(list::add), preparedStatement);
			return list;
		}
		catch (SQLException e)
		{
			throw new IOException("Selecting does not succeed.", e);
		}
	}
}
