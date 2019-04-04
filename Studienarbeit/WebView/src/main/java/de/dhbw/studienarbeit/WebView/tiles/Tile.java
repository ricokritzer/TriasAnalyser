package de.dhbw.studienarbeit.WebView.tiles;

import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Tile extends VerticalLayout
{
	private static final long serialVersionUID = 1L;
	
	private int currentView = 0;
	private List<Div> components;

	public Tile(Div... component)
	{
		setSizeFull();
		setAlignItems(Alignment.STRETCH);
		this.components = Arrays.asList(component);
		components.forEach(c -> {
			c.setHeight("90%");
			add(c);
			c.setVisible(false);
		});
		
		components.get(0).setVisible(true);

		Button button = new Button(VaadinIcon.ARROW_RIGHT.create());
		button.addClickListener(e -> next());
		button.setHeight("10%");

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
