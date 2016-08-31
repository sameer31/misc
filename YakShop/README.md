# YakShop

Application for YakShop developed using Spring Boot.


# Setup

 1. Extract the zip file to any location on local computer.
 2. Open command prompt and switch to the root folder of application containing `pom.xml` and issue the command `mvn clean package` to create the executable jar `YakShop-0.0.1-SNAPSHOT` 	under target folder.
 3. In the same window run: `java -jar target/YakShop-0.0.1-SNAPSHOT.jar ` and wait for it to start up.This will start the Spring Boot application server on default port `8080`. 
 4. Once the server is started, use any REST client to test the individual WebServices. Details of the WebService are provided in document `YakShop Services.doc` with the sample request/response structure.
 
 
# Assumptions 

1. The application is developed using Spring Boot and uses embedded Tomcat container to run the webservices.The container is packaged with the application jar itself and so there is no need to use any external server to run this application's jar.
2. Application uses JSON files `yak_stock.json` and `yak_order.json` to maintain data all the application data.These will be created by the WebServices itself and no manual creation is required for the same.
3. The REST services to view the Herd and Stock status at any particular day in future, just provides a view on that particular day.They don't update the present Stock or Herd Data present in json files.
4. The first WebService to be called should be `/herd` to initialize the actual Herd data for the YakShop.This creates the `yak_stock.json` file containing the herd data at location `src/main/resources`
5. Once the herd data is created, the other WebServices can be called in any order. 
