#!/bin/sh
# ============================================================
#  vault-init.sh - Carga inicial de secretos en Vault
#  BaseSpringApi — PAS-EST-055 Etapa 2
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

vault secrets enable -path=BaseSpringApi kv-v2 2>/dev/null || echo 'KV ya habilitado'

# ── PostgreSQL (desarrollo local) ───────────────────────────
vault kv put BaseSpringApi/database/postgres \
  host=$HOST \
  port=5432 \
  username=iess_salud_user \
  password=postgres \
  bdd=iess_salud
echo '[OK] BaseSpringApi/database/postgres'

# ── Oracle DBDVP (institucional — host externo) ───────────────
vault kv put BaseSpringApi/database/oracle \
  host=192.168.29.66 \
  port=1521 \
  username=DIRGEN_OWNER \
  password=pruebas \
  service=DBDVP
echo '[OK] BaseSpringApi/database/oracle'

# ── MongoDB ───────────────────────────────────────────────────
vault kv put BaseSpringApi/database/mongo \
  host=$HOST \
  port=27017 \
  bdd=AUDITORIA_IESS \
  username=mongo_user \
  password=mongo_password \
  auth_db=admin
echo '[OK] BaseSpringApi/database/mongo'

# ── MinIO (Etapa 3 — almacenamiento documentos) ───────────────
vault kv put BaseSpringApi/storage/minio \
  url=http://$HOST:9000 \
  accessKey=minioadmin \
  secretKey=minioadmin
echo '[OK] BaseSpringApi/storage/minio'

echo '========================================'
echo ' Secretos cargados exitosamente'
echo '========================================'

echo ''
echo '--- Verificacion de secretos ---'
vault kv get BaseSpringApi/database/postgres
vault kv get BaseSpringApi/database/oracle
vault kv get BaseSpringApi/database/mongo
vault kv get BaseSpringApi/storage/minio
