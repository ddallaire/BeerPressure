# BeerPressure

Back-end application for S6 semester projet.

## Prerequisites

You will need the following things installed on your computer.

* Java SDK, version 8 is recommended
* PostgreSQL
* [Leiningen][] 2.0.0 or above
* [Tomcat7](https://tomcat.apache.org/download-70.cgi). Use the Windows Service Installer

Once Tomcat is installed, you should open tomcat-users.xml (located in C:\Program Files\Apache Software Foundation\Tomcat 9.0\conf by default) and simply add a user. Here is an exemple :
<role rolename="manager-gui"/>
<user username="tomcat" password="admin" roles="manager-gui"/>

Try to remember the credentials, as they will be used to connect to the Tomcat manager.
Refer to the [HOW-TO](https://tomcat.apache.org/tomcat-7.0-doc/manager-howto.html) for more information

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

If you want to create a new war file manually, run :
`lein uberwar`

You should then put the generated file in your /var/lib/tomcat7/webapps/ folder and rename it to ROOT.war

### Running Tests

`lein test`

### Linting

Merge the following into your `$HOME/.lein/profiles.clj` file:

`{:user {:plugins [[jonase/eastwood "0.2.6"]] }}`

To run the linter with the default set of lint warnings, use the command:

`lein eastwood`

TODO: Set list of ignored files

### Deploying

Let's go titienne! Va compléter le script `deploy_to_tomcat`!!!
