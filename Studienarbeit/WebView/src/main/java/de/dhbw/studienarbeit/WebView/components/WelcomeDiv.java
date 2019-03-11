package de.dhbw.studienarbeit.WebView.components;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;

public class WelcomeDiv extends VerticalLayout
{
	private static final long serialVersionUID = 1L;

	public WelcomeDiv()
	{
		super();

		setAlignItems(Alignment.CENTER);

		final TextArea txtAbout = new TextArea();
		txtAbout.setLabel("Unsere Studienarbeit");
		txtAbout.setReadOnly(true);
		txtAbout.setValue("Patrick Siewert" + System.lineSeparator() + "Rico Kritzer");
		add(txtAbout);

		final TextArea txtImpress = new TextArea();
		txtImpress.setLabel("Impressum");
		txtImpress.setReadOnly(true);
		txtImpress.setValue(new StringBuilder() //
				.append("Duale Hochschule").append(System.lineSeparator()) //
				.append("Erzbergerstraße 121").append(System.lineSeparator()) //
				.append("76133 Karlsruhe").append(System.lineSeparator()) //
				.append("Deutschland") //
				.toString());
		add(txtImpress);
	}
}
