# JOT - Java Online Test

JOT is a demonstration project that let's me play with some interesting 'new' technologies in order to create a simple multiple choices test. The technologies used are :

- Springboot
- Thymeleaf
- Docker

## Compilation

Execute `mvn clean install -Dfilter.properties=<your-environment.properties>` to compile, package and install the spring boot JOT application fat jar.

### Execute the applicaiton

Execute `java -jar target/online-java-test.jar` to start the Springboot application. Without any http configuration chage, the application will respond to http://localhost:8080/jot/ 

## Deploy the docker machines

Execute `mvn clean install -Dfilter.properties=<your-environment.properties> -P docker-start` to create a mysql and http servers that will communicate together to bring up to life a fully featured jot standalone application. 



## Pre register applicants

Goto the http://localhost:8080/admin/register-applicant, set the full name and email and submit your request. You will receive a