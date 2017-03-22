@ECHO off
SETLOCAL ENABLEEXTENSIONS ENABLEDELAYEDEXPANSION
DEL /Q chromedriver.exe
DEL /Q IEDriverServer.exe
DEL /Q selenium-server-standalone*.jar

:: This script cleans unused WebDriver temp files from your computer
ECHO Cleaning Temp\webdriver*...
for /d %%a in ( %USERPROFILE%\AppData\Local\Temp\webdriver* ) do rmdir /s /q %%a
ECHO Temp\*webdriver-profile...
for /d %%a in ( %USERPROFILE%\AppData\Local\Temp\*webdriver-profile ) do rmdir /s /q %%a
ECHO Clean script is finished.  Hit any key to continue.
pause>NUL
