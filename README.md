# TriasAnalyser
Analysen für den öffentlichen Verkehr, sofern dieser die TRIAS-Schnittstelle implementiert.

## Installation
1. Installieren von MySQL
2. Installieren von Java 8 (oder höher)
3. Installieren von TomCat zum Anzeigen der Analyseergebnisse

## Einrichtung
1. Erstellen Sie 2 SQL-Nutzer (einen mit Leserechten, einen mit Schreibrechten).
2. Erstellen Sie eine Konfigurationsdatei mit den Nutzern und Passwörtern (Data > Template > configuration.conf)
3. Erstellen Sie die leeren Tabellen (Data > SQL > create > createAllTables.sql)

## Datensammlung starten
1. Packen Sie die JAR-Datei gemäß der POM in Data.
2. Führen Sie die JAR-Datei aus. Verwenden Sie als Parameter optional eine Logdatei.

## Datenaufwertung starten
1. Packen Sie die JAR-Datei gemäß der POM in DataStationNeighbourLinker und DataWeatherStopLinker
2. Starten Sie beide JAR-Dateien

## Analyseergebnisse anzeigen
1. Ersetzen Sie das ROOT.war im webapps-Ordner des TomCat-Servers.
2. Starten Sie den Server neu. Dieser wird in der Standardkonfiguration das WAR eigenständig entpacken.
3. Passen Sie ggf. die Konfiguration des Servers an, um die Ergebnisse für andere Rechner zugänglich zu machen.
