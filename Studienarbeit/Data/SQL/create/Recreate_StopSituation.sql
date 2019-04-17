DROP TABLE StopSituation;
CREATE TABLE StopSituation (
        situationID varchar(25),
        version int,
        stopID int,
        
        PRIMARY KEY (situationID, version, stopID)
        );

ALTER TABLE StopSituation ADD FOREIGN KEY StopSituation_Situation (situationID, version) REFERENCES Situation(situationID, version) ON DELETE CASCADE; 
ALTER TABLE StopSituation ADD FOREIGN KEY StopSituation_Stop (stopID) REFERENCES Stop(stopID) ON DELETE CASCADE; 
