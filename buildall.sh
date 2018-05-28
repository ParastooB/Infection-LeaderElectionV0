#!/bin/bash

rm -f *.class

echo 'javac Agent.java'
javac Agent.java

echo 'javac Agents.java'
javac Agents.java

echo 'javac InfectionEngine.java'
javac InfectionEngine.java

echo 'javac SimpleInfection.java'
javac SimpleInfection.java

echo 'all files compiled'