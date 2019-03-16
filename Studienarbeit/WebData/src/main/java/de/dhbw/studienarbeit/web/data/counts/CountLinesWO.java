package de.dhbw.studienarbeit.web.data.counts;

import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JFrame;

import de.dhbw.studienarbeit.data.reader.data.count.CountLines;
import de.dhbw.studienarbeit.data.reader.data.count.CountLinesDB;
import de.dhbw.studienarbeit.web.data.update.DataUpdater;

public class CountLinesWO extends CountListWO<CountLines>
{
	private List<Object> klickbar;
	
	public CountLinesWO(Optional<DataUpdater> updater)
	{
		counter = new CountLinesDB();
		updater.ifPresent(u -> u.updateEvery(3, HOURS, this));

		JFrame frame = new JFrame();
		
		
		JButton but = new JButton("");
		but.addActionListener(e -> buttonClicked());
	}
	
	private void buttonClicked()
	{
		klickbar.forEach(Object::getClass);
	}
}
