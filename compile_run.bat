@echo off
REM Compile the Java files
@REM echo Compiling Java files...
javac -d bin src\*.java src\custom\utils\*.java
if %ERRORLEVEL% neq 0 (
    echo Compilation failed.
    pause
    exit /b
)
@REM REM Run the program
@REM echo Running the program...
java -cp bin Main
@REM pause