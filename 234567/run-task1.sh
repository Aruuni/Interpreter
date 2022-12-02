#!/bin/bash
echo '============================================'
echo
echo 'Cleaning old builds... '
echo
./gradlew -q :task1:clean
echo
echo 'Done.'
echo
echo '============================================'
echo
echo 'Building the project... '
echo
./gradlew -q :task1:build
echo
echo 'Done.'
echo
echo '============================================'
echo
echo 'Input: Demo1.dl (n == 0)'
echo
echo '---------------------'
echo
echo 'Output:'
echo
cat ./task1/Demo1.dl | ./gradlew -q :task1:run
echo
