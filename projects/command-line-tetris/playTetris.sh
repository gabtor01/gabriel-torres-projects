#!/bin/bash
echo "Compilando archivos..."

# Crear la carpeta bin si no existe
mkdir -p bin

# Compilar todos los archivos .java de src/
javac -d bin src/*.java

echo "Ejecutando programa..."
echo

# Ejecutar el programa principal
java -cp bin Main
