#!/bin/bash

# Number of times to run the tests
num_runs=$1

for (( i=1; i<=$num_runs; i++ ))
do
  echo "Run number: $i"
  mvn test
  if [ $? -ne 0 ]
  then
    echo "Tests failed on run number: $i"
    exit 1
  fi
done

echo "All $num_runs runs completed successfully."