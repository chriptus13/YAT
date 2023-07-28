SET DBNAME=daw_db
SET USERNAME=daw
SET USERPASSWORD=pass

psql -h localhost -p 5432 -d postgres -U postgres -c "DROP DATABASE IF EXISTS \"%DBNAME%\";"
psql -h localhost -p 5432 -d postgres -U postgres -c "DROP OWNED BY \"%USERNAME%\" CASCADE;"
psql -h localhost -p 5432 -d postgres -U postgres -c "DROP USER IF EXISTS \"%USERNAME%\";"
psql -h localhost -p 5432 -d postgres -U postgres -c "CREATE DATABASE \"%DBNAME%\";"
psql -h localhost -p 5432 -d %DBNAME% -U postgres -c "CREATE USER \"%USERNAME%\" WITH PASSWORD '%USERPASSWORD%';"
psql -h localhost -p 5432 -d %DBNAME% -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE \"%DBNAME%\" TO \"%USERNAME%\";"

psql -h localhost -p 5432 -d %DBNAME% -U postgres -f createTables.sql
psql -h localhost -p 5432 -d %DBNAME% -U postgres -f createFunctions.sql
psql -h localhost -p 5432 -d %DBNAME% -U postgres -f createTriggers.sql
psql -h localhost -p 5432 -d %DBNAME% -U postgres -f baseInserts.sql

pause