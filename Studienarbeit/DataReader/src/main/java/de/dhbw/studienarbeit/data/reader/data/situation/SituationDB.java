package de.dhbw.studienarbeit.data.reader.data.situation;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import de.dhbw.studienarbeit.data.reader.database.DB;

public class SituationDB extends DB<SituationData>
{
	public final List<SituationData> getSituations() throws IOException
	{
		final String sql = "SELECT situationID, max(version), text FROM Situation GROUP BY situationID, text;";
		return readFromDatabase(sql);
	}

	@Override
	protected Optional<SituationData> getValue(ResultSet result) throws SQLException
	{
		final SituationID id = new SituationID(result.getString("situationID"));
		final SituationName name = new SituationName(result.getString("text"));

		return Optional.of(new SituationData(name, id));
	}
}
