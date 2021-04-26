FROM youmoni/jdk11
COPY target/sucursal-service-0.0.1-SNAPSHOT.jar sucursal-server-1.0.0.jar

ENV com_fravega_db_host_env="172.17.0.1"
ENV com_fravega_db_port_env="3366"
ENV com_fravega_db_name_env="fravega"
ENV com_fravega_db_username_env="fravega-admin"
ENV com_fravega_db_password_env="admin1234"

ENTRYPOINT exec java \
-Dcom.fravega.db.host=${com_fravega_db_host_env} \
-Dcom.fravega.db.port=${com_fravega_db_port_env} \
-Dcom.fravega.db.name=${com_fravega_db_name_env} \
-Dcom.fravega.db.username=${com_fravega_db_username_env} \
-Dcom.fravega.db.password=${com_fravega_db_password_env} \
-jar sucursal-server-1.0.0.jar
