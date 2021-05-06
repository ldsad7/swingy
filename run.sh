#!/bin/bash

# compile files
find * -name '*.java' > sources.txt
javac -sourcepath simulator:src @sources.txt

# run Simulator file
cd simulator/src
java com.tsimonis.avaj_launcher.Simulator ../../scenarios/scenario1.txt
