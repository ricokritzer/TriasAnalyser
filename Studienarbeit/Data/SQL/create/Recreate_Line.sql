DROP TABLE Line;
CREATE TABLE Line (
        lineID int NOT NULL AUTO_INCREMENT, 
        name varchar(100),
        destination varchar(150),
        
        PRIMARY KEY (lineID)
        );