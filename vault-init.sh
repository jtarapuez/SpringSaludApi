#!/bin/sh
# ============================================================
#  vault-init.sh - Carga inicial de secretos en Vault
#  salud-geolocalizacion-api — PAS-EST-055 Etapa 2
#
#  Para recargar secretos manualmente:
#    docker-compose -f docker-compose-utilitarios.yml up vault-init
# ============================================================

if [ -z "$IP_SERVER" ] || [ "$IP_SERVER" = "localhost" ] || [ "$IP_SERVER" = "127.0.0.1" ]; then
  HOST=localhost
else
  HOST=$IP_SERVER
fi

echo '========================================'
echo " Vault Init - Host resuelto: $HOST"
echo '========================================'

vault secrets enable -path=salud-geolocalizacion-api kv-v2 2>/dev/null || echo 'KV ya habilitado'

# ── Oracle DBDVP (institucional — host externo) ───────────────
vault kv put salud-geolocalizacion-api/database/oracle \
  host=192.168.29.66 \
  port=1521 \
  username=DIRGEN_OWNER \
  password=pruebas \
  service=DBDVP
echo '[OK] salud-geolocalizacion-api/database/oracle'

# ── MongoDB ───────────────────────────────────────────────────
vault kv put salud-geolocalizacion-api/database/mongo \
  host=$HOST \
  port=27017 \
  bdd=AUDITORIA_IESS \
  username=mongo_user \
  password=mongo_password \
  auth_db=admin
echo '[OK] salud-geolocalizacion-api/database/mongo'

# ── MinIO (Etapa 3 — almacenamiento documentos) ───────────────
vault kv put salud-geolocalizacion-api/storage/minio \
  url=http://$HOST:9000 \
  accessKey=minioadmin \
  secretKey=minioadmin
echo '[OK] salud-geolocalizacion-api/storage/minio'

echo '========================================'
echo ' Secretos cargados exitosamente'
echo '========================================'

echo ''
echo '--- Verificacion de secretos ---'
vault kv get salud-geolocalizacion-api/database/oracle
vault kv get salud-geolocalizacion-api/database/mongo
vault kv get salud-geolocalizacion-api/storage/minio
