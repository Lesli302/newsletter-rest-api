# NEWSLETTER REST API

La aplicación se ejecuta con [Spring Boot](https://spring.io/projects/spring-boot/)


### Prerequisitos
Se requieren las siguientes herrammientas previamente instaladas y configuradas:
* [Java] Version 9 o posterior
* [Maven] Version 3
* [MongoDB para MacOS](https://www.mongodb.com/docs/manual/tutorial/install-mongodb-on-os-x/)

### Descarga
Debe desacargar el proyecto de la siguiente ruta para obtener una copia del proyecto en su máquina local
(https://github.com/Lesli302/newsletter-rest-api)

### Archivos de configuración
Para que la aplicación funcione correctamente, se deben de tener en cuenta los siguientes puntos:
- DB de Mongo. Se debe de tener previamente instalada e iniciada. Por default la inicia en el puerto 27017. La configuración de la BD se encuentra en el archivo **application.properties** en las propiedades:  

*spring.data.mongodb.database=newsletter*  
*spring.data.mongodb.host=localhost*  
*spring.data.mongodb.port=27017*  

- Correo electrónico. Se debe de configurar el usuario y contraseña para el envío de correo, ya que esto lo realiza utilzando los servicios de Gmail. NOTA: Se tiene cierto número envíos de correo por día.

*sspring.mail.username=eilsel.test@gmail.com*  
*spring.mail.password=azmqodlydzwvbvnn* ***NO es la contraseña del correo, debe de ser la CONTRASEÑA DE APLICACION***  


### Iniciar servicios
Para iniciar los servicios desde el IDE, debe de realizar lo siguiente:

- Compilar el proyecto  
- Seleccionar el nombre del proyecto  
- Ejecutar como SpringBootApp

ó

- Compilar el proyecto  
- Ubicar la clase *NewsletterApirestApplication.java*
- Ejecutar como java

ó

Desde la terminal:

```bash
cd ${workspace}/newsletter-apirest
```
```bash
mvn clean install
```
```bash
cd ${workspace}/newsletter-apirest/target
```
```bash
java -jar newsletter-apirest-${version}-SNAPSHOT.jar
```
