package de.dhbw.studienarbeit.data.reader.data.request;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import de.dhbw.studienarbeit.data.reader.data.Delay;
import de.dhbw.studienarbeit.data.reader.data.count.CountData;
import de.dhbw.studienarbeit.data.reader.data.line.Line;
import de.dhbw.studienarbeit.data.reader.data.line.LineDestination;
import de.dhbw.studienarbeit.data.reader.data.line.LineName;
import de.dhbw.studienarbeit.data.reader.data.station.StationID;
import de.dhbw.studienarbeit.data.reader.database.DB;

public abstract class DelayRequestDB extends DB<DelayCountData> implements DelayRequest
{
	protected final StationID stationID;

	protected List<LineName> lineNames = new ArrayList<>();
	protected List<LineDestination> lineDestinations = new ArrayList<>();

	public DelayRequestDB(StationID stationID)
	{
		this.stationID = stationID;
	}

	@Override
	public final CountData getCancelledStops() throws IOException
	{
		final List<DelayCountData> data = readFromDatabase(getCancelledSQL(), this::setValues);

		if (data.isEmpty())
		{
			return CountData.UNABLE_TO_COUNT;
		}

		return data.get(0).getCount();
	}

	protected abstract void setValues(PreparedStatement preparedStatement) throws SQLException;

	protected String getDelaySQL()
	{
		return getSQL(false);
	}

	protected String getCancelledSQL()
	{
		return getSQL(true);
	}

	protected abstract String getSQL(boolean realtimeNull);

	@Override
	protected Optional<DelayCountData> getValue(ResultSet result) throws SQLException
	{
		final Delay delay = new Delay(result.getDouble("delay"));
		final CountData count = new CountData(result.getLong("total"));

		return Optional.of(new DelayCountData(delay, count));
	}

	@Override
	public List<DelayCountData> getDelayCounts() throws IOException
	{
		return readFromDatabase(getDelaySQL(), this::setValues);
	}

	@Override
	public void setLines(Collection<Line> lines)
	{
		setLineNames(lines.stream().map(e -> e.getName()).collect(Collectors.toList()));
		setLineDestinations(lines.stream().map(e -> e.getDestination()).collect(Collectors.toList()));
	}

	public void setLineNames(Collection<LineName> names)
	{
		this.lineNames.clear();
		this.lineNames.addAll(names);
	}

	public void setLineDestinations(Collection<LineDestination> destinations)
	{
		this.lineDestinations.clear();
		this.lineDestinations.addAll(destinations);
	}
}
