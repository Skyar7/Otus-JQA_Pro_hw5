#!/usr/bin/env bash

command="mvn test"

echo "Run: " $command
eval $command
echo "End of running: " $command