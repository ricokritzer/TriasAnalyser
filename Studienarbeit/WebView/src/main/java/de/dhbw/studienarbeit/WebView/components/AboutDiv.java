package de.dhbw.studienarbeit.WebView.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextArea;

public class AboutDiv extends Div
{
	private static final long serialVersionUID = 1L;

	public AboutDiv()
	{
		final TextArea txtAbout = new TextArea();
		txtAbout.setLabel("Unsere Studienarbeit");
		txtAbout.setReadOnly(true);
		txtAbout.setValue("Patrick Siewert" + System.lineSeparator() + "Rico Kritzer");
		add(txtAbout);

		final TextArea txtImpress = new TextArea();
		txtImpress.setLabel("Impressum");
		txtImpress.setReadOnly(true);
		txtImpress.setValue(new StringBuilder() //
				.append("Duale Hochschule Baden-Württemberg Karlsruhe").append(System.lineSeparator()) //
				.append("Erzbergerstraße 121").append(System.lineSeparator()) //
				.append("76133 Karlsruhe").append(System.lineSeparator()) //
				.append("Deutschland").append(System.lineSeparator()) //
				.toString());
		add(txtImpress);

		setVisible(false);
	}
}
