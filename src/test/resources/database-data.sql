DROP TABLE IF EXISTS sucursal;
CREATE TABLE sucursal (
  id INT NOT NULL AUTO_INCREMENT,
  direccion VARCHAR(255) NOT NULL,
  latitud DECIMAL(10,8) NOT NULL,
  longitud DECIMAL(11,8) NOT NULL,
  PRIMARY KEY (id));

INSERT INTO sucursal (id,direccion,latitud,longitud) VALUES (1, "Av. Corrientes 756, C1043 CABA", -34.60266865, -58.37764204);
INSERT INTO sucursal (id,direccion,latitud,longitud) VALUES (2, "Florida 296, C1005 CABA", -34.60397500, -58.37532461);
INSERT INTO sucursal (id,direccion,latitud,longitud) VALUES (3, "Valentín Gómez 2813, C1191 CABA", -34.60461020, -58.40600908);
INSERT INTO sucursal (id,direccion,latitud,longitud) VALUES (4, "Av. Brasil 1253, C1154 CABA", -34.62718359, -58.38368109);
INSERT INTO sucursal (id,direccion,latitud,longitud) VALUES (5, "Pichincha 69, C1089 C1089ACB", -34.60834675, -58.39931744);
