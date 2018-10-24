package de.dhbw.studienarbeit.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.dhbw.studienarbeit.data.helper.DatabaseReader;

public class App
{
	public static void main(String[] args) throws SQLException, ReflectiveOperationException
	{
		final DatabaseReader reader = new DatabaseReader();
		final String sql = "SELECT * FROM Station";
		final ResultSet result = reader.readDatabase(sql);
		
		

		// TODO code
	}
}
