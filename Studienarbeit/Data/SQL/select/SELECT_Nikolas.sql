SELECT count(*) FROM Stop WHERE Stop.stationID ='de:08212:801' AND 

HOUR(timeTabledTime)=8 AND MINUTE(timeTabledTime)=44

AND DAYOFWEEK(timeTabledTime)>1 AND DAYOFWEEK(timeTabledTime)<7

AND Stop.lineID IN (257, 1306, 14, 1060)
