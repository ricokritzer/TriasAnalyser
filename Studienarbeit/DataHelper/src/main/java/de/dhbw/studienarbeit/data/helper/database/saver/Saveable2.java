package de.dhbw.studienarbeit.data.helper.database.saver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Deprecated
public interface Saveable2
{
	@Deprecated
	PreparedStatement getPreparedStatement(Connection connection) throws SQLException;
}
