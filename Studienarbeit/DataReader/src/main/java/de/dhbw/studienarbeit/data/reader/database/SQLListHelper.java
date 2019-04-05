package de.dhbw.studienarbeit.data.reader.database;

import java.util.Collection;

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
}
