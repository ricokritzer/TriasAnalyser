UPDATE Station SET Station.observe = true WHERE operator = 'kvv';

UPDATE Station SET Station.observe = false
        WHERE operator = "kvv" 
        AND stopSaved = true
        AND stationID NOT IN 
                (SELECT stationID FROM Stop, Line WHERE Stop.lineID = Line.lineID 
                        AND SUBSTRING_INDEX(Line.name,' ',1) IN ('S-Bahn', 'Straﬂenbahn', 'RE', 'RB', 'Nightliner', 'R-Bahn'));

SELECT count(*) FROM Station WHERE observe=true;