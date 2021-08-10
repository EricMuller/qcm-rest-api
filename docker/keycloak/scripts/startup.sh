#!/bin/bash
echo "Creating keycloak client in own process..."
# /tmp/create-client.sh  & disown
# &> /dev/null

/tmp/keycloak-configuration.sh  & disown
