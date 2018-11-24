package de.dhbw.studienarbeit.WebView;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import de.dhbw.studienarbeit.data.App;

@SuppressWarnings("serial")
@Route("dataCollection")
public class DataCollectionView extends VerticalLayout
{
	Button button = new Button();
	Text text = new Text("");
	
	boolean running = false;
	
	private static final Logger LOGGER = Logger.getLogger(DataCollectionView.class.getName());
	
	public DataCollectionView()
	{
		button.addClickListener(event -> startStop());
		if (App.isRunning())
		{
			button.setText("Stop");
			text.setText("Data collection running");
		}
		else
		{
			button.setText("Start");
			text.setText("Data collection not running");
		}
		add(button, text);
	}
	
	private void startStop()
	{
		if (App.isRunning())
		{
			App.stopDataCollection();
			button.setText("Start");
			text.setText("Data collection not running");
			running = false;
			return;
		}
		try
		{
			App.startDataCollection();
			button.setText("Stop");
			text.setText("Data collection running");
		}
		catch (IOException e)
		{
			Notification.show("Data collection could not be started");
			LOGGER.log(Level.WARNING, e.getMessage());
		}
		
	}
}
