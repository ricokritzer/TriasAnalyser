CREATE TABLE Correlation (
        id int AUTO_INCREMENT PRIMARY KEY,
        value1 double,
        value2 int,
        rank1 double,
        rank2 double,
        differenceSquare double);
        
INSERT INTO Correlation (value1, value2) SELECT temp, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timeTabledTime))
        FROM StopWeather, Stop, Weather
        WHERE StopWeather.weatherID = Weather.id AND StopWeather.stopID = Stop.stopID AND realtime IS NOT NULL LIMIT 100000;

CREATE TABLE V1(
        value1 double PRIMARY KEY,
        total int 
        );
        
CREATE INDEX idxV1 ON V1(value1);
        
CREATE TABLE V2(
        value2 int PRIMARY KEY,
        total int
        );

CREATE INDEX idxV2 ON V2(value2);
    
INSERT INTO V1 (value1, total) SELECT Correlation.value1, count(*) FROM Correlation GROUP BY Correlation.value1 ORDER BY Correlation.value1;
INSERT INTO V1 (value1, total) VALUES (-9999999, 0);
UPDATE Correlation SET rank1 = (SELECT 1+sum(V1.total) FROM V1 WHERE V1.value1 < Correlation.value1);
UPDATE Correlation SET rank1 = (SELECT Correlation.rank1 + (V1.total-1)/2 FROM V1 WHERE V1.value1 = Correlation.value1);

INSERT INTO V2 (value2, total) SELECT Correlation.value2, count(*) FROM Correlation GROUP BY Correlation.value2 ORDER BY Correlation.value2;
INSERT INTO V2 (value2, total) VALUES (-9999999, 0);
UPDATE Correlation SET rank2 = (SELECT 1+sum(V2.total) FROM V2 WHERE V2.value2 < Correlation.value2);
UPDATE Correlation SET rank2 = (SELECT Correlation.rank2 + (V2.total-1)/2 FROM V2 WHERE V2.value2 = Correlation.value2);

UPDATE Correlation SET differenceSquare = (rank1-rank2)*(rank1-rank2);

SELECT (1-( (6*(sum(differenceSquare)) / (COUNT(*)*(COUNT(*)*COUNT(*)-1))))) AS correlation FROM Correlation;

DROP TABLE V1;
DROP TABLE V2;
DROP TABLE Correlation;
