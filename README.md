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
```

Der vordere Teil der muss angepast werden ->

```
public static final String URL_GET = "<DEINE IP ADRESSE>:3000/items/last5"
```

#Zuständigkeiten:
# Arne Lassen:
