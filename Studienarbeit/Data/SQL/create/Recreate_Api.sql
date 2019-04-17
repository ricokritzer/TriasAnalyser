DROP TABLE Api;
CREATE TABLE Api (
        apiID int NOT NULL AUTO_INCREMENT,
        name varchar (8),
        apiKey varchar (80),
        maximumRequests int,
        url varchar (80),
        
        PRIMARY KEY (apiID)
        );
