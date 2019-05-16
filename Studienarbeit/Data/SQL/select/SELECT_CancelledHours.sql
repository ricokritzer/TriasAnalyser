DROP TABLE CancelledHours;
CREATE TABLE CancelledHours(
        id int PRIMARY KEY,
        cancelled decimal(8),
        total decimal(8),
        percentage decimal(8,5));

INSERT INTO CancelledHours(id, total) SELECT HOUR(Stop.timetabledTime) h, count(*) FROM Stop GROUP BY h;
UPDATE CancelledHours SET cancelled = (SELECT COUNT(*) FROM Stop WHERE CancelledHours.id = HOUR(Stop.timetabledTime) AND realtime IS NULL);
UPDATE CancelledHours SET percentage = cancelled/total * 100;

SELECT * FROM CancelledHours;