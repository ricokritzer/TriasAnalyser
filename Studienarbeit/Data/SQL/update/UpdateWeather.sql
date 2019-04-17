ALTER TABLE Weather
CHANGE timeStamp
        timeStamp TIMESTAMP NOT NULL
                       DEFAULT CURRENT_TIMESTAMP;
                       
ALTER TABLE Weather RENAME WeatherOld;

ALTER TABLE WeatherOld ADD weatherInformationID int, ADD CONSTRAINT FOREIGN KEY(weatherInformationID) REFERENCES WeatherInformation(id);
    
UPDATE WeatherOld SET weatherInformationID = (SELECT id FROM WeatherInformation WHERE WeatherOld.text = WeatherInformation.text) WHERE weatherInformationID IS NULL;

ALTER TABLE WeatherOld DROP text;