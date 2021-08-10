FROM jboss/keycloak:10.0.2
COPY scripts/startup.sh /opt/jboss/startup-scripts/startup.sh
COPY scripts/create-client.sh /tmp/create-client.sh
COPY scripts/keycloak-configuration.sh /tmp/keycloak-configuration.sh
COPY scripts/keycloak-configuration-helpers.sh /tmp/keycloak-configuration-helpers.sh
COPY scripts/realm_master.sh /tmp/realm_master.sh
COPY scripts/realm_myrealm.sh /tmp/realm_myrealm.sh
COPY scripts/realms.sh /tmp/realms.sh
COPY scripts/jq-1.6-linux-64 /tmp/jq


