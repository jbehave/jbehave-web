#!/bin/sh

# starts server
start()
{
	cd $MODULE
	mvn jetty:run-war
}

# ----- Execute the commands -----------------------------------------

MODULE=$1
if test -z "$MODULE"; then
MODULE="../trader-runner"
fi

start $MODULE   	

exit 1

