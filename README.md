##sucursal-service

Api para crear y consultar los datos de una sucursal en particular o la mas cercana a una localización dada


## Construir
	> mvn clean package -U


## Variables de entorno
Se deben configuar las siguientes variables de entorno
- com.fravega.db.host=localhost
- com.fravega.db.port=3306
- com.fravega.db.name=fravega
- com.fravega.db.username=username
- com.fravega.db.password=password


## Ejecución 
Consola

    > java -Dcom.fravega.db.host= -Dcom.fravega.db.port= -Dcom.fravega.db.name= -Dcom.fravega.db.username= -Dcom.fravega.db.password= -Dserver.port= -jar target/sucursal-service-0.0.1-SNAPSHOT.jar

Docker

    > mvn clean package
    > docker build -t fravega/sucursal-service .
    > docker run -p 8888:8182 fravega/sucursal-service


## Endpoints
Buscar una sucursal por id
- GET http://hostname:port/sucursal/{id}

        Response
        {
            "id": 1,
            "direccion": "Av. Corrientes 756, C1043 CABA",
            "latitud": -34.60266865,
            "longitud": -58.37764204
        }
\
Agregar una sucursal nueva
- PUT http://hostname:port/sucursal/
        
        Body Params
        {
            "direccion": "Av. Corrientes 3217, C1193 CABA",
            "latitud": -34.60337512867464, 
            "longitud": -58.41025770060735
        }
        
        Response
        {
            "id": 6,
            "direccion": "Av. Corrientes 3217, C1193 CABA",
            "latitud": -34.60337512867464,
            "longitud": -58.41025770060735
        }
\
Buscar la sucursal mas cercana a las coordenadas
- GET http://hostname:port/sucursal/mas-cercana/?latitud=-34.611111293772545&longitud=-58.3942502774778

        URL Params
        -latitud[double]
        -longitud[double]
        
        Response
        {
            "id": 5,
            "direccion": "Pichincha 69, C1089 C1089ACB",
            "latitud": -34.60834675,
            "longitud": -58.39931744
        }

\
Health check
- GET http://hostname:port/actuator/health/


## Documentación
http://hostname:port/swagger-ui.html
