SELECT DISTINCT Station.name, max(Stop.timeTabledTime) AS maxTime
FROM Station, Stop
WHERE Station.stationID = Stop.stationID
AND Stop.lineID = 1
GROUP BY Station.name