@echo off
echo Building Tagonn Android App...
echo.

echo Cleaning previous build...
call gradlew clean

echo.
echo Building debug APK...
call gradlew assembleDebug

echo.
echo Build completed!
pause