## Prerequisites
1. Install tomcat 10.1.18 https://tomcat.apache.org/download-10.cgi
2. Install JDK 17
3. Update the path to JDK, tomcat home and base-dir in env-variable
4. Update the path to the sqllite DB file. in the DB classes.

### IntelliJ way
1. ./idea/runConfigurations has the runConfiguration which will work on intelliJ after updating the paths correctly.
2. Just hit run


### Through command line (maybe difficult)
1. Update the path to JDK, tomcat, pacakge name in the run.bat file
2. Run the command
3. After build succeeds
4. Run the tomcat part of the bat file. (for some reason both don't run together)
 
