#!/bin/bash
# ChocAn Unit Test Runner
# Written by Wheeler Knight on 12/4/2025
# Edited by Wheeler Knight on 12/5/2025 - Added Gson library to classpath
# Edited by Wheeler Knight on 12/5/2025 - JSON data now stored in database/ folder

echo "==================================="
echo "ChocAn Unit Test Runner"
echo "==================================="
echo ""

# Check if JUnit is available
JUNIT_JAR="/usr/share/java/junit4.jar"
HAMCREST_JAR="/usr/share/java/hamcrest-core.jar"
GSON_JAR="lib/gson-2.10.1.jar"

if [ ! -f "$JUNIT_JAR" ]; then
    echo "JUnit not found at $JUNIT_JAR"
    echo "Please install JUnit 4: sudo apt-get install junit4"
    echo ""
    echo "Alternative: Download JUnit from https://junit.org/junit4/"
    exit 1
fi

if [ ! -f "$GSON_JAR" ]; then
    echo "Gson library not found at $GSON_JAR"
    echo "Downloading Gson..."
    mkdir -p lib
    curl -L -o "$GSON_JAR" https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar
fi

# Ensure database folder exists
if [ ! -d "database" ]; then
    echo "Creating database folder..."
    mkdir -p database
fi

# Create output directory
mkdir -p bin/tests

echo "Compiling source files..."
javac -cp "$GSON_JAR" -d bin src/chocan/*.java 2>&1

if [ $? -ne 0 ]; then
    echo "Source compilation failed!"
    exit 1
fi

echo "Compiling test files..."
javac -d bin/tests -cp "bin:$JUNIT_JAR:$HAMCREST_JAR:$GSON_JAR" tests/chocan/*.java 2>&1

if [ $? -ne 0 ]; then
    echo "Test compilation failed!"
    exit 1
fi

echo ""
echo "Running tests..."
echo "-----------------------------------"
java -cp "bin:bin/tests:$JUNIT_JAR:$HAMCREST_JAR:$GSON_JAR" org.junit.runner.JUnitCore chocan.AllTests

echo ""
echo "==================================="
echo "Test run complete!"
echo "==================================="
