@ECHO off
TITLE Jetty Server on 8080
ECHO Running from: %CD%
:: starts server
IF NOT DEFINED STARTDIR (
    SET "STARTDIR=..\trader-runner"
)
CD %STARTDIR%
mvn.bat clean package jetty:run-war -Djbehave.webrunner.version=3.5.5

ECHO Jetty was killed. Hit any key to continue...
pause>NUL
