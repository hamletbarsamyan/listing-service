# listing-service
Listing application backend

### Required softwares

	a) java 11
	b) database server - mysql 8

### Running application
Run the following scripts in setup/sql/ folder:

    database_schema.sql 
	sales_inserts.sql

Set configuration settings in support/HOME/config/

    application.properties 

For running application from IDE set environment variables

    SPRING_CONFIG_LOCATION=/PATH_TO/HOME/config/
    SPRING_CONFIG_NAME=application

For running application from command line

    java -jar listings.jar --spring.config.location=/PATH_TO/HOME/config/ --spring.config.name=application


Run as service
sudo ln -s /path_to_listings_jar/listings.jar /etc/init.d/listings-service
sudo service listings-service start
