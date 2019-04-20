package de.dhbw.studienarbeit.data.reader.data.line;

import de.dhbw.studienarbeit.data.reader.data.DelayDB;
import de.dhbw.studienarbeit.data.reader.data.DelayDBTest;

public class DelayLineDBTest extends DelayDBTest<Line>
{
	@Override
	protected DelayDB<Line> getClassUnderTest()
	{
		return new DelayLineDB();
	}
}
