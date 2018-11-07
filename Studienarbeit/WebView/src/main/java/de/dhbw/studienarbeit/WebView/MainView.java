package de.dhbw.studienarbeit.WebView;

import java.io.IOException;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import de.dhbw.studienarbeit.data.helper.database.SqlCondition;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableLine;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableStation;
import de.dhbw.studienarbeit.data.helper.database.table.DatabaseTableStop;

/**
 * The main view contains a button and a click listener.
 */
@SuppressWarnings("serial")
@Route("")
public class MainView extends VerticalLayout {

    public MainView() {
    	DatabaseTableStation dbtStation = new DatabaseTableStation();
		DatabaseTableLine dbtLine = new DatabaseTableLine();
		DatabaseTableStop dbtStop = new DatabaseTableStop();
    	Text txtStation1 = new Text("");
    	Text txtLine1 = new Text("");
    	Text txtStop1 = new Text("");
    	Text txtStation2 = new Text("");
    	Text txtLine2 = new Text("");
    	Text txtStop2 = new Text("");
    	Text txtStation3 = new Text("");
    	Text txtLine3 = new Text("");
    	Text txtStop3 = new Text("");
        
    	add(txtStation1);
    	add(txtLine1);
    	add(txtStop1);
    }
}
