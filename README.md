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

Soll die App ausgeführt werden, müssen innerhalb von Android Studio in der Api Java Klasse die zwei Konstanten "URL_GET" und "URL_POST" geändert werden. Da unsere Rest-Api auf Localhost läuft, müssen die URL's auf die IP Adresse des Computers auf welchem die Rest-Api läuft, angepasst werden.

```
public static final String URL_GET = "http://192.168.2.111:3000/items/last5";
public static final String URL_POST = "http://192.168.2.111:3000/items/";
```

Der vordere Teil der muss angepast werden ->

```
public static final String URL_GET = "<DEINE IP ADRESSE>:3000/items/last5"
public static final String URL_POST = "<DEINE IP ADRESSE>:3000/items/";
```


#Zuständigkeiten(Implementierung):
<br>
Arne Lassen:
- MainActivity Klasse & Xml
- QrCodeScannerActivity Klasse & Xml
- Design der App
- Rest-Api(JavaScript / MongoDB)
- Überarbeiten der Api Klasse(Java)
- RestApi Klasse (Wird nicht benutzt)
- Gemeinsames Debuggen, der MainActivity, damit URL's korrekt aufgerufen werden können, und das crashen der App verhindert wird

Marius Bünner:
- MainActivity Klasse (CustomListView)
- Erstellen der verschiedenen Activities
- Erstellen der Api Klasse(Java)
- Erstellen der CustomListView
- Gemeinsames Debuggen, der MainActivity, damit URL's korrekt aufgerufen werden können, und das crashen der App verhindert wird
- Hilfestellung beim Debuggen der Rest-Api Schnittstelle

#Zuständigkeiten(Dokumentation)
Arne Lassen:
Marius Bünner:
