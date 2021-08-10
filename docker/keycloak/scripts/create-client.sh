#!/bin/bash
export PATH=$PATH:$JBOSS_HOME/bin

AUTH_ENDPOINT=http://localhost:8080/auth/

while ! curl -s --head  --request GET $AUTH_ENDPOINT | grep "200 OK" > /dev/null; do
  echo "Waiting for Keycloak server..."
  sleep 5s
done


# credentials
kcadm.sh config credentials --server $AUTH_ENDPOINT --realm master --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD

#realms
REALM=qcm

realm=$(kcadm.sh get realms/$REALM)

echo "realm qcm = $realm"

if ! [ -z "$realm" ]; then
  echo "delete realm $REALM"
  kcadm.sh delete realms/$REALM
fi

echo "create realm $REALM"
kcadm.sh create realms -s realm=$REALM -s enabled=true
echo "update realm $REALM"

kcadm.sh update realms/$REALM -s registrationAllowed=true -s registrationEmailAsUsername=true -s rememberMe=true -s verifyEmail=false -s resetPasswordAllowed=true -s editUsernameAllowed=true

realm=$(kcadm.sh get realms/$REALM)



# credentials
kcreg.sh config credentials --server $AUTH_ENDPOINT --realm master --user $KEYCLOAK_USER --password $KEYCLOAK_PASSWORD


CLIENT_ID=qcm-web-dev
client=$(kcreg.sh get $CLIENT_ID)

if [ -z "$client" ]; then
    kcreg.sh create -s clientId=$CLIENT_ID -s 'redirectUris=["http://localhost:4200/*"]' -s 'publicClient=true'
else
    echo "The client $CLIENT_ID has already been created."
fi


CLIENT_BACK_ID=qcm-rest-api
client=$(kcreg.sh get $CLIENT_BACK_ID)

if [ -z "$client" ]; then
    kcreg.sh create -s clientId=$CLIENT_BACK_ID -s 'redirectUris=["http://localhost:4200/*"]' -s 'publicClient=true'
else
    echo "The client $CLIENT_BACK_ID has already been created."
fi



