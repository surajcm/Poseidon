# Poseidon

Poseidon is a service center management software. Designed as a solution for keeping the inventory and billing for a laptop service centre.

![Poseidon](http://s12.postimg.org/3xei02465/txn_2.png)


##Features
* Provide a functionality to create a service ticket and a workflow based on it
* Provides a final invoice to customer
* Various reports to gain insights on improving the business

## To contribute / try out in dev mode : set up the following apps in your dev environment

* JDK 1.6 or higher(1.8 is preferred)
* MySQL Community server (Database), MySql workbench latest versions
* tomcat / JBoss
* Java IDE (Preferred Intellij IDEA Community Edition)
* Git (with out this also it will work fine, as Intellij & Eclipse support checkout from the IDE itself)

## Steps for testing the Application
* Install all the above
* Download the war file from the dist directory
* Create a user localuser in mysql (with password as localuser)
* Restore the db file from the code ( a file named databaseScripts.sql is available at WebContent/WEB_INF/databaseDesign directory)
* Assign desired permission to the localuser for the new schema
* Put the war file in the app server deployment directory ( jboss/server/default/deploy for jboss & webapps in tomcat)
* Start the server
* In http://localhost:8080/Poseidon/ our application will be up and running.
* Either create a database user name as username and password as password, or edit the same in Poseidon/WebContent/WEB-INF/applicationCotext.xml
* In Mysql work bench insert a restore script which is available in Poseidon/WebContent/WEB-INF/databaseDesign/databaseScripts.sql
* try log in with admin/admin. Explore !!!

## Technologies used
* spring multi action controller
* jquery
* jdbc to connect to the MySQL db
* Twitter bootstrap 3
* Jasper reports
* juni4
* ant

## The changes we plan
* Hibernate
* Internationalization of pages (web pages, its partially done now)
* spring boot
* gradle
