# BeerPressure

Back-end application for S6 semester projet.

## Prerequisites

You will need the following things installed on your computer.

* Java SDK, version 8 is recommended
* PostgreSQL
* [Leiningen][] 2.0.0 or above
* [Tomcat7](https://tomcat.apache.org/download-70.cgi).

Once Tomcat is installed, you should open tomcat-users.xml and simply add a user. Here is an example :
<role rolename="manager-gui"/>
<user username="tomcat" password="admin" roles="manager-gui"/>

Try to remember the credentials, as they will be used to connect to the Tomcat manager.

The recommended IDE is [Intellij IDEA Ultimate](https://www.jetbrains.com/student/). It can be obtained for free using your usherbrooke email.

You should also add the Cursive plugin, which provides full Clojure support.

[leiningen]: https://github.com/technomancy/leiningen

## Running / Development

You need these environment variables to be able to run the application correctly:

```
db-host
db-port
db-name
db-username
db-password
```

You can manage these environment variables by creating a `.lein-env` in the root of the project. Here is an example of its content:

```
{:db-host "localhost"
 :db-port "5432"
 :db-name "beerpressure"
 :db-username "postgres"
 :db-password ""}
```

To start a web server for the application, run:

`lein ring server`

### Running Tests

`lein test`

### Linting

Merge the following into your `$HOME/.lein/profiles.clj` file:

`{:user {:plugins [[jonase/eastwood "0.2.6"]] }}`

To run the linter with the default set of lint warnings, use the command:

`lein eastwood`

TODO: Set list of ignored files

### Deploying

If you want to create a new war file manually, run :
`lein uberwar`

Take the newly generated .jar file in your target folder and put it in your /var/lib/tomcat7/webapps/ folder. Rename it to ROOT.war

Then, to deploy, you simply need to navigate to your Tomcat Web Application Manager (localhost:8080/manager/html by default) and upload the file.

Refer to the [HOW-TO](https://tomcat.apache.org/tomcat-7.0-doc/manager-howto.html) page for more information
