@echo off

title FilzaNix Virtual Environment
cd /d "%~dp0"

color 07
cls

if not exist out mkdir out

javac -encoding UTF-8 -d out ^
src\main\java\com\filzanix\Main.java ^
src\main\java\com\filzanix\shell\Shell.java ^
src\main\java\com\filzanix\filesystem\VirtualFileSystem.java

if errorlevel 1 (
    color 0C
    echo.
    echo [ERROR] Compilation failed.
    pause
    exit /b 1
)

java -cp out com.filzanix.Main

echo.
pause