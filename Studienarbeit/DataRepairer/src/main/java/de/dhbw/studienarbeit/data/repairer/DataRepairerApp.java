package de.dhbw.studienarbeit.data.repairer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.dhbw.studienarbeit.data.helper.database.DatabaseSaver;
import de.dhbw.studienarbeit.data.helper.database.Saver;
import de.dhbw.studienarbeit.data.helper.datamanagement.MyTimerTask;

public class DataRepairerApp
{
	private static final Logger LOGGER = Logger.getLogger(DataRepairerApp.class.getName());
	private Saver saver;

	public static void main(String[] args) throws IOException
	{
		new DataRepairerApp(new DatabaseSaver());
	}

	public DataRepairerApp(final Saver saver)
	{
		this.saver = saver;
	}

	public void startDataRepairing()
	{
		Timer t = new Timer();
		t.schedule(new MyTimerTask(this::repairData), new Date(), 60 * 60 * 1000l); // each hour
		LOGGER.log(Level.INFO, "Repairing scheduled.");
	}

	protected void repairData()
	{
		final List<String> dataToRepair = dataToRepair();
		deleteErrorFile();
		dataToRepair.forEach(data -> saver.save(new RepairData(data.split("\t")[1])));
		LOGGER.log(Level.INFO, "Repairing completed.");
	}

	private void deleteErrorFile()
	{
		final File file = new File("errors.txt");

		try
		{
			if (Files.deleteIfExists(file.toPath()))
			{
				LOGGER.log(Level.INFO, "File deleted.");
			}
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Deleting file does not succeed.", e);
		}
	}

	private List<String> dataToRepair()
	{
		final List<String> dataToRepair = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("errors.txt")))
		{
			String read;
			while (Optional.ofNullable(read = br.readLine()).isPresent())
			{
				dataToRepair.add(read);
			}
			LOGGER.log(Level.INFO, "Repairing started: " + dataToRepair.size());

		}
		catch (FileNotFoundException e)
		{
			LOGGER.log(Level.INFO, "No errors found.");
		}
		catch (IOException e)
		{
			LOGGER.log(Level.WARNING, "Not able to repair errors.", e);
		}

		return dataToRepair;
	}
}
