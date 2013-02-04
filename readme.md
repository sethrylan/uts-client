# UTS Client

A test client for UTS API 2.0 (UMLS Terminology Services, replacement to UMLSKS).

[UTS Docs](https://uts.nlm.nih.gov/doc/ws/)

[UTS Javadocs](https://uts.nlm.nih.gov/doc/ws/javadocs/)

Also includes experimental LFU/LRU cache implementations.

## Build Instructions

Add the *.nlm.nih.gov certificate to the Maven keystore:
```
keytool -import -trustcacerts -alias *.nlm.nih.gov -file ".\certs\nlm.nih.gov.cer" -keystore %MAVEN_HOME%\conf\trust.jks
```

Build in maven
```
mvn clean install
```

##License

Released under the [MIT license](http://www.opensource.org/licenses/mit-license.php).
