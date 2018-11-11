#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE dbshipping;
    GRANT ALL PRIVILEGES ON DATABASE dbshipping TO dbuser;
    CREATE DATABASE dborder;
    GRANT ALL PRIVILEGES ON DATABASE dborder TO dbuser;
EOSQL
