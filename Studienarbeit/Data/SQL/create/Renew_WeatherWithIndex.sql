CREATE TABLE Weather2 (
        id int NOT NULL AUTO_INCREMENT,
        lat decimal(8,2),
        lon decimal(8,2),
        timeStamp timestamp,
        temp decimal(6,2),
        humidity decimal(6,2),
        pressure decimal(6,2),
        wind decimal(6,2),
        clouds decimal(6,2),
        text varchar(50),
        
        primary key (id)
        );

INSERT INTO Weather2 (lat, lon, timeStamp, temp, humidity, pressure, wind, clouds, text) SELECT * FROM Weather;

DROP TABLE Weather;

CREATE TABLE Weather (
        id int NOT NULL AUTO_INCREMENT,
        lat decimal(8,2),
        lon decimal(8,2),
        timeStamp timestamp,
        temp decimal(6,2),
        humidity decimal(6,2),
        pressure decimal(6,2),
        wind decimal(6,2),
        clouds decimal(6,2),
        text varchar(50),
        
        primary key (id)
        );

INSERT INTO Weather SELECT * FROM Weather2;

DROP TABLE Weather2;
