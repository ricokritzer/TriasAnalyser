DROP TABLE Api;
CREATE TABLE Api (
        apiID int NOT NULL AUTO_INCREMENT,
        name varchar (8),
        apiKey varchar (80),
        maximumRequests int,
        url varchar (80),
        
        PRIMARY KEY (apiID)
        );
        
INSERT INTO Api (name, apiKey, maximumRequests, url) VALUES('kvv', '7qzuxfx8EPvH', 400, 'http://185.201.144.208/kritzertrias/trias');
INSERT INTO Api (name, apiKey, maximumRequests, url) VALUES('bbb', '', 400, 'http://ogdtrias.verbundlinie.at:8183/stv/trias');
INSERT INTO Api (name, apiKey, maximumRequests, url) VALUES('weather', '9412f9cf01e1de32009e18c8276ea082', 58, 'https://api.openweathermap.org/data/2.5/weather');
INSERT INTO Api (name, apiKey, maximumRequests, url) VALUES('weather', 'b5923a1132896eba486d603bc6602a5f', 58, 'https://api.openweathermap.org/data/2.5/weather');
INSERT INTO Api (name, apiKey, maximumRequests, url) VALUES('weather', '1f61487689aaffbcf3b6615626a5676c', 58, 'https://api.openweathermap.org/data/2.5/weather');
INSERT INTO Api (name, apiKey, maximumRequests, url) VALUES('weather', '92b1bfdfb2ea5300ea1ea5c3d8684f2d', 58, 'https://api.openweathermap.org/data/2.5/weather');
INSERT INTO Api (name, apiKey, maximumRequests, url) VALUES('weather', '0fbd16c9ad63fc112f8ba02396131bd4', 58, 'https://api.openweathermap.org/data/2.5/weather');
INSERT INTO Api (name, apiKey, maximumRequests, url) VALUES('weather', '525c18e8c547c755f16ff1a6d0f422a2', 58, 'https://api.openweathermap.org/data/2.5/weather'); 



