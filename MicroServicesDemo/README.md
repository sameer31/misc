# MicroServicesDemo

Demo application for MicroServices using Spring Boot approach.


# Setup

You may find it easier to view the different applications by running them from a command line since you can place the three windows side-by-side and watch their log output

To do this, open three CMD windows (Windows) or three Terminal windows (MacOS, Linux) and arrange so you can view them conveniently.

 1. Extract the zip file to any location on local computer.
 2. Switch to the root folder containing `pom.xml` and issue the command `mvn clean package` to create the executable jar `MicroServicesDemo-0.0.1-SNAPSHOT` 	under target folder.
 3. In the same window run: `java -jar target/MicroServicesDemo-0.0.1-SNAPSHOT.jar registration` and wait for it to start up.This will start the registration 	server on default port `8080`. 
 4. Open a second window and run: `java -jar target/MicroServicesDemo-0.0.1-SNAPSHOT.jar product` and wait for
 	it to start up.This will start the Product server on default port `8082`.
 5. Open a third window and run: `java -jar target/MicroServicesDemo-0.0.1-SNAPSHOT.jar pricing` and wait for
 	it to start up.This will start the Pricing server on default port `8081`.
 6. Open the link in browser to check the services registration status [http://localhost:8080](http://localhost:8080).
 7. Once the services are registered, use any REST client to test the individual WebServices. Details of the WebService are provided in `MicroServicesDemo Services.doc`.
 
 
# Assumptions 

1. For this demo we are using in-memory databases for each service with no sync on communication between two and so data has to be inserted in each database individually using the REST WebService provided for Product and Pricing. 
2. Instead of using Id's we are using nouns like `product_name` and `product_type` in URL's to simplify the demo.
3. `product_price` is assumed to be an integer. 
