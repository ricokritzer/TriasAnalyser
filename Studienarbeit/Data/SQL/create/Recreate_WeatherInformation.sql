DROP TABLE WeatherInformation;
CREATE TABLE WeatherInformation(
        id int AUTO_INCREMENT,
        text varchar(50),
        textDE varchar(50),
        icon varchar(3),
        
        primary key (id)
        );
        
CREATE INDEX idx_textDE ON WeatherInformation(textDE);
CREATE INDEX idx_icon ON WeatherInformation(icon);
        
INSERT INTO WeatherInformation (text, textDE, icon)  
SELECT * FROM WeatherIcon;

ALTER TABLE Weather ADD weatherInformationID int;
ALTER TABLE Weather ADD FOREIGN KEY weather_weatherInformationID (weatherInformationID) REFERENCES WeatherInformation(id);