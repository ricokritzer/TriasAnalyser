package de.dhbw.studienarbeit.data.trias;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

class StationTest
{

	@Test
	void equalMethodReturnsTrue_forTwoStops_ThatAreEqual()
	{
		Line line = new Line("test", "test");
		Stop stop1 = new Stop("test", line, new Date(1000), new Date(2000));
		Stop stop2 = new Stop("test", line, new Date(1000), new Date(2000));
		assertThat(stop1.equals(stop1), is(true));
		assertThat(stop1.equals(stop2), is(true));
	}
	
	@Test
	void equalMethodReturnsTrue_forTwoStops_ThatHaveDifferentRealTime()
	{
		Line line = new Line("test", "test");
		Stop stop1 = new Stop("test", line, new Date(1000), new Date(2000));
		Stop stop2 = new Stop("test", line, new Date(1000), new Date(3000));
		assertThat(stop1.equals(stop2), is(true));
	}
	
	@Test
	void sqlStatementForLine()
	{
		Line line = new Line("name", "destination");
		assertThat(line.getSQLQuerry(), is("INSERT INTO Line (name, destination) VALUES ('name', 'destination')"));
	}
	
	@Test
	void testName()
	{
		
	}

}
