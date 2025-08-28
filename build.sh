#!/bin/bash

echo "Building Tagonn Android App..."
echo

echo "Cleaning previous build..."
./gradlew clean

echo
echo "Building debug APK..."
./gradlew assembleDebug

echo
echo "Build completed!"