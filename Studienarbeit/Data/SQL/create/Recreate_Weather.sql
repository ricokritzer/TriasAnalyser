DROP TABLE Weather;
CREATE TABLE Weather (
        lat decimal(8,2),
        lon decimal(8,2),
        timeStamp timestamp,
        temp decimal(6,2),
        humidity decimal(6,2),
        pressure decimal(6,2),
        wind decimal(6,2),
        clouds decimal(6,2),
        text varchar(50),
        
        primary key (lat, lon, timeStamp)
        );