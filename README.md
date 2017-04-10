# JOT - Java Online Test

JOT is a demonstration project that let's me play with some interesting 'new' technologies in order to create a simple multiple choices test. The technologies used are :

- SpringBoot
- Thymeleaf
- Docker

## Compilation

Create a default.properties at the level of the pom.xml with all the properties below set to your environment and execute `mvn clean install` to compile package and install the spring boot JOT application fat jar, or `mvn clean install -Dfilter.properties=<your-environment.properties>` with a properties file in a different place.

### Application properties example
<pre>
dbHost=jot-mysql-service<br/>
dbPort=3306<br/>
dbUsername=jot<br/>
dbPassword={the db password you want}<br/>
adminUsername=admin<br/>
adminPassword={the admin password you want}<br/>
delayBetweenAttemps=-1<br/>
cvsRepository={path where cvs will be saved}<br/>
defaultQuestionnaireId=1<br/>
jotLoginModel={PIN|SIMPLE_EMAIL_SESSION}<br/>
jotPinValidityInDays=7<br/>
jotUrl={jot url ex: http://localhost:8080/}<br/>
hrDistributionMail={hr email, that will receive all success/failures emails}<br/>
// by default only starttls mailserver allowed ... but i'll clean it soon
smtpHost={the smtp host}<br/>
smtpPort={the smtp port}<br/>
smtpUsername={your smtp user name}<br/>
smtpPassword={your smtp user password}<br/>
smtpFrom={from email}<br/>
</pre>

### Execute the application

Execute `java -jar target/online-java-test.jar` to start the SpringBoot application. Without any http configuration change, the application will respond to http://localhost:8080/jot/ 

## Deploy the docker machines

Execute `mvn clean install -Dfilter.properties=<your-environment.properties> -P docker-start` to create a mysql and http servers that will communicate together to bring up to life a fully featured jot standalone application. 

## Pre register applicants

Goto the http://localhost:8080/admin/register-applicant, set the full name and email and submit your request. A pin will be generated that will allow a one time access to the Jot assessment.

Then go to http://localhost:8080/jot/ and put the email used to generate the pin an the pin and start the assessment. No page refresh navigation allowed, no page refresh allowed, after the time out the next question is loaded. If there is more than 2 seconds between question max time and post request - get request than it probably  means that you tried to cheat (javascript modifications) and the questionnaire will fail.  
