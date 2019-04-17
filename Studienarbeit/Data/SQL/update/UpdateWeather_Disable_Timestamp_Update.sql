ALTER TABLE Weather
CHANGE timeStamp
        timeStamp TIMESTAMP NOT NULL
                       DEFAULT CURRENT_TIMESTAMP;