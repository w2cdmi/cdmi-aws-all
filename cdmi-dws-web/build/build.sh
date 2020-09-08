#!/bin/bash

profileActive=dev
if [ -n "$1" ]; then
    profileActive=$1
fi

mvn clean package -Dmaven.test.skip=true -P${profileActive} -f ../pom.xml