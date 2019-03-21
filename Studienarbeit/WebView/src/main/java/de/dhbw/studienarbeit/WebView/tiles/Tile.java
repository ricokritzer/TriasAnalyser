package de.dhbw.studienarbeit.WebView.tiles;

import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class Tile extends HorizontalLayout
{
	private static final long serialVersionUID = 1L;
	
	private int currentView = 0;
	private List<Component> components;

	public Tile(List<Component> components)
	{
		this.components = components;
		components.forEach(c -> {
			add(c);
			c.setVisible(false);
		});
		
		components.get(0).setVisible(true);

		Button button = new Button("next");
		button.addClickListener(e -> next());

		add(button);
	}

	protected void next()
	{
		components.get(currentView).setVisible(false);
		currentView++;
		if (currentView >= components.size())
		{
			currentView = 0;
		}
		components.get(currentView).setVisible(true);
	}

}
