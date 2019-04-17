DROP TABLE WeatherIcon;
CREATE TABLE WeatherIcon(
        text varchar(50),
        textDE varchar(50),
        icon varchar(3),
        
        primary key (text)
        );
        
INSERT INTO WeatherIcon VALUES ('thunderstorm with light rain', '', '11d');
INSERT INTO WeatherIcon VALUES ('thunderstorm with rain', '', '11d');
INSERT INTO WeatherIcon VALUES ('thunderstorm with heavy rain', '', '11d');
INSERT INTO WeatherIcon VALUES ('light thunderstorm', '', '11d');
INSERT INTO WeatherIcon VALUES ('thunderstorm', '', '11d');
INSERT INTO WeatherIcon VALUES ('heavy thunderstorm', '', '11d');
INSERT INTO WeatherIcon VALUES ('ragged thunderstorm', '', '11d');
INSERT INTO WeatherIcon VALUES ('thunderstorm with light drizzle', '', '11d');
INSERT INTO WeatherIcon VALUES ('thunderstorm with drizzle', '', '11d');