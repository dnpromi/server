@echo on
REM Define your project's Maven goals and options
set MAVEN_GOALS=clean package
REM Optional: Skip tests during the build if needed
set MAVEN_OPTS=-DskipTests

REM Set the path to your Maven executable (replace with your actual path)
set MAVEN_EXECUTABLE=C:\Users\dpanda\IdeaProjects\cointracker3\mvnw.cmd

REM Set the path to your Tomcat installation directory (replace with your actual path)
set TOMCAT_DIR=C:\Users\dpanda\IdeaProjects\cointracker\apache-tomcat-10.1.18

REM Define the name of your WAR file (replace with your project's WAR file name)
set WAR_FILE=cointracker3.war

REM Build the Maven project
echo Building the Maven project...
%MAVEN_EXECUTABLE% %MAVEN_GOALS% %MAVEN_OPTS%

echo Stopping Tomcat...
%TOMCAT_DIR%\bin\catalina.bat stop

echo Removing old WAR file...
del %TOMCAT_DIR%\webapps\%WAR_FILE%

echo Deploying the new WAR file...
copy target\%WAR_FILE% %TOMCAT_DIR%\webapps\

echo Starting Tomcat...
%TOMCAT_DIR%\bin\catalina.bat start

echo Tailing Tomcat logs...
type %TOMCAT_DIR%\logs\catalina.out
