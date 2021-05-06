#!/bin/bash

# remove generated classes and other resources
rm -rf sources.txt simulator/src/simulation.txt
find * -name "*.class" -delete
