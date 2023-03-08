# MobileApplicationAusarbeitung

**Autoren** <br>
**Arne Lassen** <br>
**Github: Harambe93** <br>
**30167993** <br>
**lassen.arne@fh-swf.de** <br>
**Marius Bünner** <br>
**Github: 0x57686f616d49** <br>
**30183230** <br>
**buenner.marius@fh-swf.de** <br>

Soll die App ausgeführt werden, müssen innerhalb von Android Studio in der Api Java Klasse unter \Ausarbeitung\Implementierung\app\src\main\java\ma\ws2022\barcodeapp\Api die zwei Konstanten "URL_GET" und "URL_POST" geändert werden. Da unsere Rest-Api auf Localhost läuft, müssen die URL's auf die IP Adresse des Computers, auf welchem die Rest-Api läuft, angepasst werden.

```
public static final String URL_GET = "http://192.168.2.111:3000/items/last5";
public static final String URL_POST = "http://192.168.2.111:3000/items/";
```

Der vordere Teil der muss angepast werden ->

```
public static final String URL_GET = "<DEINE IP ADRESSE>:3000/items/last5"
public static final String URL_POST = "<DEINE IP ADRESSE>:3000/items/";
```

Anschließend muss im Ordner "Api" - \Ausarbeitung\Implementierung\Api - Ein neues Terminal geöffnet werden, und dort die beiden Befehle

```
npm install
```

und 

```
node index js
```

Eingegeben werden, damit der Rest-Api Server gestartet wird. Im Standard Fall läuft die Api auf dem Port 3000. Falls das nicht gewünscht ist, muss die PORT Konstante ganz am Anfang des index.js Files innerhalb des Api Ordners, geändert werden.

#Zuständigkeiten(Implementierung):
<br>
Arne Lassen:
- MainActivity Klasse & Xml
- QrCodeScannerActivity Klasse & Xml
- Design der App
- Rest-Api(JavaScript / MongoDB)
- Überarbeiten der Api Klasse(Java) -> Fixen der fillTextView Methode, und hinzufügen der sendJsonPostRequest Methode
- RestApi Klasse (Wird nicht benutzt)
- Gemeinsames Debuggen, der MainActivity, damit URL's korrekt aufgerufen werden können, und das crashen der App verhindert wird
- Schreiben der README

Marius Bünner:
- MainActivity Klasse (CustomListView)
- Erstellen der verschiedenen Activities
- Erstellen der Api Klasse(Java)
- Erstellen der CustomListView
- Gemeinsames Debuggen, der MainActivity, damit URL's korrekt aufgerufen werden können, und das crashen der App verhindert wird
- Hilfestellung beim Debuggen der Rest-Api Schnittstelle

#Zuständigkeiten(Dokumentation) <br>

Arne Lassen:<br>
- Einleitung
- Die App
- Persona(Zusammenarbeit im Zoom)
- Anforderungsanalyse(Zusammenarbeit im Zoom)
- Systemkontext(Zusammenarbeit im Zoom)
- Klassendiagramm(Zusammenarbeit im Zoom)
- Scanner
- Api 10.1 Node.Js
<br>

Marius Bünner:<br>
- Überführung der Dokumente in LaTex
- Persona(Zusammenarbeit im Zoom)
- Anforderungsanalyse(Zusammenarbeit im Zoom)
- Systemkontext(Zusammenarbeit im Zoom)
- Klassendiagramm(Zusammenarbeit im Zoom)
- Strukturierung der App
- CustomListView
- Api 10.2 Api Klasse
