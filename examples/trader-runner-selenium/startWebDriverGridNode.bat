@ECHO off
SETLOCAL ENABLEDELAYEDEXPANSION
SET TITLETEXT=WebDriver Grid Node on 5555
TITLE %TITLETEXT%


:: Proxy for Fiddler or for BrowserMob
SET PROXY=false


SET CHROMEDRIVERZIP=chromedriver_win32.zip
SET CHROMEDRIVERVER=2.8
SET CHROMEDRIVER=chromedriver.exe
SET JAR=selenium-server-standalone-2.39.0.jar
SET IEDRIVERZIP=IEDriverServer_Win32_2.39.0.zip
SET IEDRIVER=IEDriverServer.exe
SET "WGET=C:\Program Files (x86)\GnuWin32\bin\wget.exe"
 
ECHO *********************************************
ECHO *
ECHO * WebDriver Grid Node
ECHO * It requires that a WebDriver JSON Hub is already running, usually on port 4444 .
ECHO * You can run more than one of these if each has its own JSON config file.
ECHO *
ECHO *********************************************
ECHO.


IF NOT DEFINED JAVA_HOME (
ECHO You must define a JAVA_HOME environment variable before you run this script.
GOTO :ERROR
)
SET "PATH=.;%JAVA_HOME%\bin;%PATH%"
 
IF NOT EXIST %JAR% (
IF EXIST "%WGET%" (
"%WGET%" --dot-style=binary http://selenium.googlecode.com/files/%JAR%
) ELSE (
ECHO Wget.exe is missing. Install GNU utils. & GOTO :ERROR
)
)
 
IF NOT EXIST %IEDRIVER% (
IF EXIST "%WGET%" (
"%WGET%" --dot-style=binary http://selenium.googlecode.com/files/%IEDRIVERZIP%
jar.exe xvf %IEDRIVERZIP%
DEL /Q %IEDRIVERZIP%
) ELSE (
ECHO Wget.exe is missing. & GOTO :ERROR
)
)
 
IF NOT EXIST %CHROMEDRIVER% (
IF EXIST "%WGET%" (
"%WGET%" --dot-style=binary --no-check-certificate http://chromedriver.storage.googleapis.com/%CHROMEDRIVERVER%/%CHROMEDRIVERZIP%
jar.exe xvf %CHROMEDRIVERZIP%
DEL /Q %CHROMEDRIVERZIP%
) ELSE (
ECHO Wget.exe is missing. & GOTO :ERROR
)
)
 
ECHO.
ECHO ======================
ECHO Grid Hub status : & netstat -an | FIND "4444"
IF NOT %ERRORLEVEL%==0 (
  ECHO Hub is required to run first.
  :: You can disable this check if you need to run a Node on a different computer.
  ECHO.
  GOTO :ERROR
)
ECHO ======================
ECHO.


:: Set JAVA_OPTS java options to JVM
SET "JAVA_OPTS=-Dwebdriver.chrome.driver=%CD%\%CHROMEDRIVER%"
IF "%PROXY%"=="true" SET "JAVA_OPTS=%JAVA_OPTS% -DproxySet=true -Dhttp.proxyHost=127.0.0.1 -Dhttp.proxyPort=8888"


TITLE %TITLETEXT%
java.exe -jar %JAR% -role node -nodeConfig node1Config.json %JAVA_OPTS%
 
GOTO :END
:ERROR
pause
:END

