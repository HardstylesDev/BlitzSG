@echo off
setlocal

set "ROOT=%~dp0"
set "ORIGINAL_PATH=%PATH%"

set "JDK8=C:\Program Files\Eclipse Adoptium\jdk-8.0.472.8-hotspot"
set "JDK_MODERN=C:\Program Files\Eclipse Adoptium\jdk-25.0.1.8-hotspot"

if not exist "%JDK8%\bin\java.exe" (
  echo [ERROR] JDK8 not found at %JDK8%
  exit /b 1
)

if not exist "%JDK_MODERN%\bin\java.exe" (
  echo [ERROR] Modern JDK not found at %JDK_MODERN%
  exit /b 1
)

echo [1/2] Building main plugin with Java 8...
set "JAVA_HOME=%JDK8%"
set "PATH=%JAVA_HOME%\bin;%ORIGINAL_PATH%"
pushd "%ROOT%"
call mvn -U clean package
if errorlevel 1 goto :failMain
popd

echo [2/2] Building Velocity proxy with Java 25...
set "JAVA_HOME=%JDK_MODERN%"
set "PATH=%JAVA_HOME%\bin;%ORIGINAL_PATH%"
pushd "%ROOT%blitz-proxy"
call mvn -U clean package
if errorlevel 1 goto :failProxy
popd

echo [SUCCESS] Both builds completed.
exit /b 0

:failMain
popd
echo [ERROR] Main plugin build failed.
exit /b 1

:failProxy
popd
echo [ERROR] Proxy build failed.
exit /b 1
