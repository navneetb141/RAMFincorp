@echo off
title Restart ResearchMonitor Service
echo.
echo ===============================
echo Checking service status BEFORE restart...
sc query ResearchMonitor
echo ===============================
echo.

echo Stopping ResearchMonitor service...
sc stop ResearchMonitor
ping -n 3 127.0.0.1 >nul

echo.
echo ===============================
echo Checking service status AFTER stop...
sc query ResearchMonitor
echo ===============================
echo.

echo Starting ResearchMonitor service...
sc start ResearchMonitor
ping -n 3 127.0.0.1 >nul

echo.
echo ===============================
echo Checking service status AFTER start...
sc query ResearchMonitor
echo ===============================
echo.

echo Restart process complete.
