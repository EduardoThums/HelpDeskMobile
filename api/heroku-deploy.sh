#!/bin/bash

mvn clean

mvn install -Pdev -DprofileIdEnabled=true

heroku deploy:jar target/help-desk-mobile-api.jar -a help-desk-mobile-api