package de.dhbw.studienarbeit.data.helper.database.saver;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Saveable
{
	/*
	 * @return SQLQuerry with '?' instead of variables.
	 */
	String getSQLQuerry();

	/*
	 * @param Prepared statement using getSQLQuerry().
	 */
	void setValues(PreparedStatement preparedStatement) throws SQLException;
}
