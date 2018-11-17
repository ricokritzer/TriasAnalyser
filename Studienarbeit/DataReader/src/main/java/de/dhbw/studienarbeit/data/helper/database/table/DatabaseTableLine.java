package de.dhbw.studienarbeit.data.helper.database.table;

import de.dhbw.studienarbeit.data.helper.database.model.Count;

public class DatabaseTableLine extends DatabaseTable
{
	private static final String TABLE_NAME = "Line";

	/*
	 * use Count.countLines() instead.
	 */
	@Deprecated
	public Count count()
	{
		return Count.countLines();
	}
}
