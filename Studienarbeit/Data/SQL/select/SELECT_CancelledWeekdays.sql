DROP TABLE CancelledWeekdays;
CREATE TABLE CancelledWeekdays(
        id int PRIMARY KEY,
        name char(2),
        cancelled decimal(8),
        total decimal(8),
        percentage decimal(8,5));
        
INSERT INTO CancelledWeekdays(id, name) VALUES (1, "So");
INSERT INTO CancelledWeekdays(id, name) VALUES (2, "Mo");
INSERT INTO CancelledWeekdays(id, name) VALUES (3, "Di");
INSERT INTO CancelledWeekdays(id, name) VALUES (4, "Mi");
INSERT INTO CancelledWeekdays(id, name) VALUES (5, "Do");
INSERT INTO CancelledWeekdays(id, name) VALUES (6, "Fr");
INSERT INTO CancelledWeekdays(id, name) VALUES (7, "Sa");

UPDATE CancelledWeekdays SET cancelled = (SELECT COUNT(*) FROM Stop WHERE CancelledWeekdays.id = DAYOFWEEK(Stop.timetabledTime) AND realtime IS NULL);
UPDATE CancelledWeekdays SET total = (SELECT COUNT(*) FROM Stop WHERE CancelledWeekdays.id = DAYOFWEEK(Stop.timetabledTime));
UPDATE CancelledWeekdays SET percentage = cancelled/total * 100;

SELECT * FROM CancelledWeekdays;