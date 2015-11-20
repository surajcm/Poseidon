# Poseidon

Poseidon is a service center management software, primarily designed as a solution for keeping the inventory and billing for a laptop service centre.

![Poseidon](http://s12.postimg.org/3xei02465/txn_2.png)


## To contribute / try out in dev mode : set up the following apps in your dev environment

* JDK 1.6 or higher(1.8 is preferred)
* MySQL Community server (Database), MySql workbench latest versions
* JBoss 4.2.2 GA (higher versions can be used, if the configurations are updated correctly)
* Intellij IDEA 14 Community Edition or your preferred IDE
* Git (with out this also it will work fine, as Intellij & Eclipse support checkout from the IDE itself)

## Steps for testing the Application
* Install all the above
* Download the war file from the dist directory
* Create a user localuser in mysql (with password as localuser)
* Restore the db file from the code ( a file named databaseScripts.sql is available at WebContent/WEB_INF/databaseDesign folder)
* Assign desired permission to the localuser for the new schema
* Put the war file in jboss/server/default/deploy directory
* Start the JBoss server from jboss/run.bat , In http://localhost:8080/Poseidon/ our application will be up and running.
* Either create a database user name as username and password as password, or edit the same in Poseidon/WebContent/WEB-INF/applicationCotext.xml
* In Mysql work bench insert a restore script which is available in Poseidon/WebContent/WEB-INF/databaseDesign/databaseScripts.sql

Now everything will work as expected.

## Current Features
* Uses spring multi action controller
* Uses jquery
* Uses jdbc to connect to the MySQL db
* Twitter bootstrap
* Jasper reports integration (partially done)

## The changes we plan
* Hibernate
* Add Unit test - junit 3
* Ability to read data from a property file
* Mock the db , and should be able to be live on mock mode
* Internationalization of pages (web pages, its partially done now)

