#!/usr/bin/env bash
mvn clean install
mv target/cocoapebbles-twitter-1.0.jar ../../server/plugins
#sudo mv target/cocoapebbles-twitter-1.0.jar /home/ubuntu/Desktop/server/plugins