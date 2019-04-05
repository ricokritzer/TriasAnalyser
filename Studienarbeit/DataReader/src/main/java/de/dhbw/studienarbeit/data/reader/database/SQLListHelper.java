package de.dhbw.studienarbeit.data.reader.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class SQLListHelper
{
	public static String createSQLFor(String fieldname, Collection<? extends Object> list)
	{
		if (list.isEmpty())
		{
			return "";
		}

		final StringBuilder sb = new StringBuilder(fieldname).append(" IN (?");

		for (int i = 1; i < list.size(); i++)
		{
			sb.append(", ?");
		}

		return sb.append(")").toString();
	}

	public static int setValue(int idx, PreparedStatement preparedStatement, List<String> strings) throws SQLException
	{
		for (String string : strings)
		{
			preparedStatement.setString(idx, string);
			idx++;
		}
		return idx;
	}
}
