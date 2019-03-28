# heaven_hr
Heavenhr back-end java application assignment

Backend application which that handles a recruiting process, below are the core tech-stack used to implement

Java programming language
JDK1.8
Spring-boot, Spring framework
h2 in memory database
Gradle
IDE inteliJ
Swagger

The project is implemented using IDE InteliJ & used basic auth in memory. 
Use below credentials while accessing services.
User:- heavenhr
Password:- password


I have used the h2 database(http://localhost:8082/h2-console) that runs in memory to store the data.
Post importing application into workspace you have to use below gradle command to start the application
clean build assemble bootRun

Above command will also create the application.jar executable jar file, you can execute only that also using command "java -jar <path_to_jar>/application.jar" 

It will take some time to start the application post that you can access the services as below. 

Swagger URL http://<your_machine_ip>:8082/swagger-ui.html or http://localhost:8082/swagger-ui.html  to test the services.
