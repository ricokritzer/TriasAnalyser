CREATE TABLE Correlation (
        id int AUTO_INCREMENT PRIMARY KEY,
        value1 decimal(6,2),
        sum decimal(8),
        cancelled decimal(8),
        value2 decimal(8),
        rank1 decimal(10,2),
        rank2 decimal(10,2),
        differenceSquare double);
        
INSERT INTO Correlation (value1) SELECT DISTINCT clouds FROM Weather;

UPDATE Correlation SET sum = (SELECT count(*) FROM StopWeather, Stop, Weather
        WHERE StopWeather.weatherID = Weather.id AND StopWeather.stopID = Stop.stopID AND value1 = clouds);

DELETE FROM Correlation WHERE sum = 0;

UPDATE Correlation SET cancelled = (SELECT count(*) FROM StopWeather, Stop, Weather
        WHERE StopWeather.weatherID = Weather.id AND StopWeather.stopID = Stop.stopID AND realtime IS NULL AND value1 = clouds);
UPDATE Correlation SET cancelled = 0 WHERE cancelled IS NULL;

UPDATE Correlation SET value2 = cancelled/sum * 1000;

CREATE TABLE V1(
        value1 decimal(6,2) PRIMARY KEY,
        total decimal(8),
        startrank decimal(8),
        rank decimal(10,2) 
        );
CREATE TABLE V1Copy(
        value1 decimal(6,2) PRIMARY KEY,
        total decimal(8)
        );
        
CREATE TABLE V2(
        value2 decimal(8) PRIMARY KEY,
        total decimal(8),
        startrank decimal(8),
        rank decimal(10,2)
        );
CREATE TABLE V2Copy (
        value2 decimal(8) PRIMARY KEY,
        total decimal(8)
        );
    
INSERT INTO V1 (value1, total) SELECT Correlation.value1, count(*) FROM Correlation GROUP BY Correlation.value1 ORDER BY Correlation.value1;
UPDATE V1 SET startrank = 1 ORDER BY value1 LIMIT 1;

INSERT INTO V1Copy SELECT value1, total FROM V1;
UPDATE V1 SET startRank = (SELECT 1+sum(V1Copy.total) FROM V1Copy WHERE V1Copy.value1 < V1.value1) WHERE startrank IS NULL;
DROP TABLE V1Copy;

UPDATE V1 SET rank = startRank + (total -1)/2;
UPDATE Correlation SET rank1 = (SELECT rank FROM V1 WHERE V1.value1 = Correlation.value1);
DROP TABLE V1;

INSERT INTO V2 (value2, total) SELECT Correlation.value2, count(*) FROM Correlation GROUP BY Correlation.value2 ORDER BY Correlation.value2;
UPDATE V2 SET startrank = 1 ORDER BY value2 LIMIT 1;

INSERT INTO V2Copy SELECT value2, total FROM V2;
UPDATE V2 SET startRank = (SELECT 1+sum(V2Copy.total) FROM V2Copy WHERE V2Copy.value2 < V2.value2) WHERE startrank IS NULL;
DROP TABLE V2Copy;

UPDATE V2 SET rank = startRank + (total -1)/2;
UPDATE Correlation SET rank2 = (SELECT rank FROM V2 WHERE V2.value2 = ROUND(Correlation.value2,4));
DROP TABLE V2;

UPDATE Correlation SET differenceSquare = (rank1-rank2)*(rank1-rank2);

SELECT * FROM Correlation;

SELECT sum(differenceSquare) FROM Correlation;
SELECT COUNT(*) FROM Correlation;

DROP TABLE Correlation;