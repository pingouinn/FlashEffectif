@echo off
del /Q /S /F ".\bin\*.*"
javac -d .\bin -cp ".;libs/json-20210307.jar" ".\src\**\*.java"
echo Compilation terminee.

