package de.dhbw.studienarbeit.WebView.components;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.timepicker.TimePicker;

public class DateTimePicker extends CustomField<Date>
{
	private static final long serialVersionUID = 1L;

	private DatePicker date;
	private TimePicker time;

	public DateTimePicker(LocalTime defaultTime)
	{
		date = new DatePicker(LocalDate.now());
		time = new TimePicker(defaultTime);
		add(date, time);
	}

	@Override
	public void setReadOnly(boolean readOnly)
	{
		date.setReadOnly(readOnly);
		time.setReadOnly(readOnly);
	}
	
	@Override
	public Optional<Date> getOptionalValue()
	{
		if (!date.getOptionalValue().isPresent() || !time.getOptionalValue().isPresent())
		{
			return Optional.empty();
		}

		LocalDate localDate = date.getValue();
		LocalTime localTime = time.getValue();
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.SECOND, localTime.getSecond());
		cal.set(Calendar.MINUTE, localTime.getMinute());
		cal.set(Calendar.HOUR_OF_DAY, localTime.getHour());
		cal.set(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());
		cal.set(Calendar.MONTH, localDate.getMonthValue() - 1);
		cal.set(Calendar.YEAR, localDate.getYear());

		return Optional.of(cal.getTime());
	}

	@Override
	protected Date generateModelValue()
	{
		return getOptionalValue().orElse(new Date());
	}

	@Override
	protected void setPresentationValue(Date newPresentationValue)
	{
		// intentional left blank
	}
}
