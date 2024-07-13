# INICIO DE LA APLICACIÓN
La presente aplicación pretende simular los servicios de una veterinaria donde, 
además de prestar los diferentes servicios de urgencia, atención y Spa, también 
venderá productos en la modalidad de comercio electrónico. La aplicación está 
basada en Backend y está siendo realizada con Java con Spring Boot en su versión 
3.x una base de datos PostgreSQL manejada por medio de un contenedor de Docker 
inicialmente y como ORM estamos usando JPA/Hibernate.

### Desarrollado por: ###
Desarrollador de Backend: [Juan Sebastian Medina Toro](https://www.linkedin.com/in/juan-sebastian-medina-toro-887491249/)

### Levantamiento de la aplicación:
Para correr la aplicación en ambiente de desarrollo necesitamos:

* Levante el contenedor, ejecute el comando:
````dockerfile
$ docker compose up -d
````

* Ejecute la semilla por medio del siguiente End Point:
````dockerfile
$ Pendiente por definición.
````

* La documentación del proyecto está en la siguiente URL:
````dockerfile
$ http://localhost:9990/swagger-ui/index.html
````
...

### Consideraciones de Trabajo ###
* *Modelo de Datos*: El modelo de datos inicial se encuentra en la carpeta ```docs``` en la raíz del proyecto,
  esta carpeta contendrá un esquema de tablas para entender genéricamente el funcionamiento de la aplicación,
  el objetivo es que en esta carpeta se depositará documentos adicionales, sabemos que no es una buena práctica,
  sin embargo, por temas de revisiones futuras y entendimiento de reclutadores se hará así.
* *Arquitectura*: 
  * *Entidades*: Tendremos los modelos de las tablas, su estructura estará para lectura JPA/Hibernate y con Lombok.
  * *DTOS*: Esquemas de como se espera recibir la información de las peticiones.
  * *Helpers*: Funciones generales para temas de respuesta, estructuras de respuesta, paginaciones y validadores genéricos
  * *Controladores*: Recepción de las peticiones y maquetan la estructura de respuesta
  * *Servicios*: Lógica de negocio, se realiza la interfaz y la implementación concreta
  * *Repositorios*: La conexión a JPA e Hibernate

