@echo off
echo ============================================
echo Cleaning old builds... 
echo(
cmd /c "gradlew -q :task1:clean"
echo(
echo Done.
echo(
echo ============================================
echo(
echo Building the project...
echo( 
cmd /c "gradlew -q :task1:build "
echo(
echo Done.
echo(
echo ============================================
echo(
echo Input: Demo1.dl
echo(
echo ---------------------
echo(
echo Output:
echo(
cmd /c "type .\task1\Demo1.dl | .\gradlew  :task1:run   --args "33""
echo(
echo ============================================
echo(
echo Input: Demo2.dl
echo(
echo ---------------------
echo(
echo Output:
echo(
cmd /c "type .\task1\Demo2.dl | .\gradlew -q :task1:run
echo ============================================
echo(
echo Input: Demo3.dl
echo(
echo ---------------------
echo(
echo Output:
echo(
cmd /c "type .\task1\Demo3.dl | .\gradlew -q :task1:run

