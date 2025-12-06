@echo off echo Compilando archivos... 
rem Crear bin\ para separar los binarios o ejecutables 
if not exist bin mkdir bin 

rem Compilar todos los archivos de src\ 
javac -d bin src\*.java 

rem Informar que ya se va a ejecutar 
echo Ejecutando programa... 
echo. 
rem Ejecutar el punto de inicio java -cp bin Main 
pause
