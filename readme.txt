#Configure the postgre database with valid connection details
#Path - ems\src\main\resources\application.properties

#Build the project from the root folder of project (It will also execute Integeration Test)
mvn clean install

#Start the application
mvn spring-boot:run

#After successful start, hit the swagger url for API Documentation
http://localhost:8080/swagger-ui.html

