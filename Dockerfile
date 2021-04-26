FROM youmoni/jdk11
COPY target/sucursal-service-0.0.1-SNAPSHOT.jar sucursal-server-1.0.0.jar

ARG com_fravega_db_host='mariadb'
ARG com_fravega_db_port='3366'
ARG com_fravega_db_name='fravega'
ARG com_fravega_db_username='fravega-admin'
ARG com_fravega_db_password='admin1234'

ENV com_fravega_db_host_env="${com_fravega_db_host}"
ENV com_fravega_db_port_env="${com_fravega_db_port}"
ENV com_fravega_db_name_env="${com_fravega_db_name}"
ENV com_fravega_db_username_env="${com_fravega_db_username}"
ENV com_fravega_db_password_env="${com_fravega_db_password}"

ENTRYPOINT exec java \
-Dcom.fravega.db.host=${com_fravega_db_host_env} \
-Dcom.fravega.db.port=${com_fravega_db_port_env} \
-Dcom.fravega.db.name=${com_fravega_db_name_env} \
-Dcom.fravega.db.username=${com_fravega_db_username_env} \
-Dcom.fravega.db.password=${com_fravega_db_password_env} \
-jar sucursal-server-1.0.0.jar

