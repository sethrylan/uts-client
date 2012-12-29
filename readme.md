# UTS Client

A test client for UTS API 2.0 (UMLS Terminology Services, replacement to UMLSKS).

[UTS Docs](https://uts.nlm.nih.gov/doc/ws/)
[UTS Javadocs](https://uts.nlm.nih.gov/doc/ws/javadocs/)

Also includes experimental LFU/LRU cache implementations.

## Build Instructions
mvn clean install

** Additional install steps:

Add the *.nlm.nih.gov certificate to the Maven keystore:
```
keytool -import -trustcacerts -alias *.nlm.nih.gov -file "C:\Users\gaineys.FWT2HS1\Downloads\certs\nlm.nih.gov.cer" -keystore "C:\Projects\Chapter33\Trunk\Build\Tools\maven\conf\trust.jks"
```

