#!/bin/bash

# Script to run all Java examples
echo "Java Examples Runner"
echo "===================="

# List of all example classes
EXAMPLES=(
    "oop.ConstructorExample"
    "oop.RectangleExample" 
    "oop.PhoneExample"
    "oop.InheritanceExample"
    "collections.CollectionsExample"
    "modern.ModernJavaFeatures"
    "advanced.ExceptionHandlingExample"
    "advanced.FileIOExample"
    "basic.EnumExample"
)

# Build the project first
echo "Building project..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "Build failed! Exiting."
    exit 1
fi

echo -e "\nRunning examples...\n"

# Run each example
for example in "${EXAMPLES[@]}"; do
    echo "=========================================="
    echo "Running: $example"
    echo "=========================================="
    mvn exec:java -Dexec.mainClass="$example" -q
    echo -e "\nPress Enter to continue to next example..."
    read
done

echo "All examples completed!"