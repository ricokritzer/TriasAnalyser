DROP TABLE Operator;
CREATE TABLE Operator(
        operator varchar (8),
        contact varchar(50),
        displayName varchar(50),
        
        PRIMARY KEY(operator)
);

INSERT INTO Operator VALUES ('kvv', 'Maximilian.Obenaus@kvv.karlsruhe.de', 'Karlsruher Verkehrsverbund');
INSERT INTO Operator VALUES ('bbb', 'manfred.brandl@verbundlinie.at', 'Veröffentlichung nicht gestattet.');
INSERT INTO Operator VALUES ('test', 'test', 'Testdaten.');