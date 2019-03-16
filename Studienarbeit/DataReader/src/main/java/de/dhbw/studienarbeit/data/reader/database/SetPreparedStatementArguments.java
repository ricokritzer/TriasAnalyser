package de.dhbw.studienarbeit.data.reader.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SetPreparedStatementArguments
{
	void accept(PreparedStatement t) throws SQLException;
}
