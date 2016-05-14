# Running the Server

Make sure that PostgreSQL and Maven are installed on your computer, and that you
have an account capable of editing the database you would like to use.

If you do not already have a postgres account to use your database from, you need
to create one. This can be done like this:
```
sudo su postgres

createuser -sl -P myusername
```

You also need to create a database to use. This can be done with the command
```
createdb dbname
```
on an account with the ability to create databases. Log out of postgres (```exit```)

Then you need to make a config file for the server - ```example-config.yml```
can be used for this. Make sure the database you point to exists.

Next, compile and package the server with ```mvn package```. This will create a
jar file in the directory ```target```, along the lines of "dbserver-VERSION.jar".
In case tests are failing, you can use the option ```-Dmaven.test.skip=true```.

Run the jar file with the config file. For example,
```java -jar target/dbserver-VERSION.jar server config.yml```. The server should
now be running.