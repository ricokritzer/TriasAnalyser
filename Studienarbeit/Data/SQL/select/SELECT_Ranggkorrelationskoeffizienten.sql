CREATE TABLE Correlation (
        id int AUTO_INCREMENT PRIMARY KEY,
        value1 double,
        value2 int,
        rank1 double,
        rank2 double,
        differenceSquare double);
        
INSERT INTO Correlation (value1, value2) SELECT temp, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timeTabledTime))
        FROM StopWeather, Stop, Weather
        WHERE StopWeather.weatherID = Weather.id AND StopWeather.stopID = Stop.stopID AND realtime IS NOT NULL LIMIT 100;

CREATE TABLE V1(
        id int AUTO_INCREMENT PRIMARY KEY,
        value1 double);
        
CREATE INDEX idxV1 ON V1(value1);
        
CREATE TABLE V2(
        id int AUTO_INCREMENT PRIMARY KEY,
        value2 int);

CREATE INDEX idxV2 ON V2(value2);
    
INSERT INTO V1 (value1) SELECT value1 FROM Correlation ORDER BY value1;
UPDATE Correlation SET rank1 = (SELECT COUNT(*)+1+(1/(SELECT COUNT(*) FROM V1 WHERE V1.value1 = Correlation.value1)) FROM V1 WHERE V1.value1 < Correlation.value1);

INSERT INTO V2 (value2) SELECT value2 FROM Correlation ORDER BY value2;
UPDATE Correlation SET rank2 = (SELECT COUNT(*)+1+(1/(SELECT COUNT(*) FROM V2 WHERE V2.value2 = Correlation.value2)) FROM V2 WHERE V2.value2 < Correlation.value2);

UPDATE Correlation SET differenceSquare = (rank1-rank2)*(rank1-rank2);

SELECT * FROM Correlation;

SELECT (1-( (6*(sum(differenceSquare)) / (COUNT(*)*(COUNT(*)*COUNT(*)-1))))) AS correlation FROM Correlation;

DROP TABLE V1;
DROP TABLE V2;
DROP TABLE Correlation;
