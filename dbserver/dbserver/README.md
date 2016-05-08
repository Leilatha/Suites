# Running the Server

First, you need to make a config file for the server - ```example-config.yml```
can be used for this. Make sure the database you point to exists.

Next, compile and package the server with ```mvn package```. This will create a
jar file in the directory ```target```, along the lines of "dbserver-VERSION.jar".

Run the jar file with the config file. For example,
```java -jar target/dbserver-VERSION.jar server config.yml```. The server should
now be running.