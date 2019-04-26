# MejorCocina
ejemplo de App prueba practica de desarrollo

app web sencilla creada usando vaadin como framework para desarrollo web (frontEnd) , hibernate para la capa de persistencia y maven para gestionar
las dependencias.

para desplegar el .war:
- En el directorio out/artifacts se encuentra el .war para ser desplegado
- dentro del .war modificar el archivo Hibernate hibernate.cfg.xml y cambiar los valores de conexion a la BD, en este caso Postgres, se 
puede usar cualquier manejador de BD, solo se debe cambiar el Driver y resolver las dependencias del mismo.

este es un proyecto .idea pero puede ser compilado en cualquier IDE.
