package de.dhbw.studienarbeit.data.helper.database.saver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Saveable2
{
	PreparedStatement getPreparedStatement(Connection connection) throws SQLException;
}
