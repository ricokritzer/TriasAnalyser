CREATE TABLE Correlation (
        id int AUTO_INCREMENT PRIMARY KEY,
        value1 double,
        value2 int,
        rank1 double,
        rank2 double,
        differenceSquare double);
        
INSERT INTO Correlation (value1, value2) SELECT temp, (UNIX_TIMESTAMP(realtime) - UNIX_TIMESTAMP(timeTabledTime))
        FROM StopWeather, Stop, Weather
        WHERE StopWeather.weatherID = Weather.id AND StopWeather.stopID = Stop.stopID AND realtime IS NOT NULL;

CREATE TABLE V1(
        value1 double PRIMARY KEY,
        total int,
        startrank int,
        rank double 
        );
CREATE INDEX idxV1 ON V1(value1);
CREATE TABLE V1Copy LIKE V1;
CREATE INDEX idxV1Copy ON V1Copy(value1);
        
CREATE TABLE V2(
        value2 int PRIMARY KEY,
        total int,
        startrank int,
        rank double
        );
CREATE INDEX idxV2 ON V2(value2);
CREATE TABLE V2Copy LIKE V2;
CREATE INDEX idxV2Copy ON V2Copy(value2);
    
INSERT INTO V1 (value1, total) SELECT Correlation.value1, count(*) FROM Correlation GROUP BY Correlation.value1 ORDER BY Correlation.value1;
INSERT INTO V1 (value1, total, startrank, rank) VALUES (-9999999,0, 0, 0);
INSERT INTO V1Copy SELECT * FROM V1;

UPDATE V1 SET startRank = (SELECT 1+sum(V1Copy.total) FROM V1Copy WHERE V1Copy.value1 < V1.value1) WHERE rank IS NULL;
UPDATE V1 SET rank = startRank + (total -1)/2;
UPDATE Correlation SET rank1 = (SELECT rank FROM V1 WHERE V1.value1 = Correlation.value1);

INSERT INTO V2 (value2, total) SELECT Correlation.value2, count(*) FROM Correlation GROUP BY Correlation.value2 ORDER BY Correlation.value2;
INSERT INTO V2 (value2, total, startrank, rank) VALUES (-9999999,0, 0, 0);
INSERT INTO V2Copy SELECT * FROM V2;

UPDATE V2 SET startRank = (SELECT 1+sum(V2Copy.total) FROM V2Copy WHERE V2Copy.value2 < V2.value2) WHERE rank IS NULL;
UPDATE V2 SET rank = startRank + (total -1)/2;
UPDATE Correlation SET rank2 = (SELECT rank FROM V2 WHERE V2.value2 = Correlation.value2);

UPDATE Correlation SET differenceSquare = (rank1-rank2)*(rank1-rank2);

SELECT (1-( (6*(sum(differenceSquare)) / (COUNT(*)*(COUNT(*)*COUNT(*)-1))))) AS correlation FROM Correlation;

DROP TABLE V1;
DROP TABLE V1Copy;
DROP TABLE V2;
DROP TABLE V2Copy;
DROP TABLE Correlation;
