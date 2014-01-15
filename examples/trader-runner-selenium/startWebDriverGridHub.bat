@ECHO off
SETLOCAL ENABLEDELAYEDEXPANSION
SET TITLETEXT=WebDriver Grid Hub on 4444
TITLE %TITLETEXT%
 
ECHO *********************************************
ECHO *
ECHO * WebDriver grid Hub instance.
ECHO *
ECHO *  http://localhost:4444/grid/console
ECHO *
ECHO *********************************************
ECHO.
 
SET JAR=selenium-server-standalone-2.39.0.jar
SET "WGET=C:\Program Files (x86)\GnuWin32\bin\wget.exe"
 
IF NOT DEFINED JAVA_HOME (
  ECHO You must define a JAVA_HOME environment variable before you run this script.
  GOTO :ERROR
)
SET "PATH=%JAVA_HOME%\bin;%PATH%"
 
IF NOT EXIST %JAR% (
  ECHO Selenium standalone server .jar is missing.
  IF EXIST "%WGET%" (
    "%WGET%" --dot-style=binary http://selenium.googlecode.com/files/%JAR%
        TITLE %TITLETEXT%
  ) ELSE (
    ECHO Wget.exe is missing. Install GNU Utils.
    ECHO.
        GOTO :ERROR
  )
)
ECHO.
 
java.exe -jar %JAR% -role hub -hubConfig hubConfig.json -debug
IF NOT %ERRORLEVEL%==0 GOTO :ERROR
 
GOTO :END
:ERROR
ECHO There may have been an error.  Try running the script again.
pause
:END
pause
