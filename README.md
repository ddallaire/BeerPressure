# BeerPressure

Back-end application for S6 semester projet.

## Prerequisites

You will need the following things installed on your computer.

* Java SDK must me installed, version 8 is recommended.
* PostgreSQL
* You will need [Leiningen][] 2.0.0 or above installed.

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

{:user {:plugins [[jonase/eastwood "0.2.6"]] }}

To run the linter with the default set of lint warnings, use the command:

`lein eastwood`

TODO: Set list of ignored files

### Deploying

Let's go titienne! Va compléter le script `deploy_to_tomcat`!!!
