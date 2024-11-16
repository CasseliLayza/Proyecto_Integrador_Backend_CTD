# Proyecto Integrador Backend - Digital House

Este es un proyecto de backend desarrollado en Java con Spring Boot para el **Proyecto Integrador** de Digital House. El sistema está diseñado para gestionar una plataforma de alquiler de autos de lujo. El backend proporciona las funcionalidades necesarias para la creación, actualización y eliminación de autos, categorías, características, usuarios, así como el envío de correos electrónicos de notificación, reservas y mucho mas, todo esto con un control de acceso y autenticacion utilizando **Spring Security** y **JWT**.

## Tecnologías Utilizadas

- **Java 17**: Lenguaje de programación utilizado para el desarrollo del backend.
- **Spring Boot 2.7.17**: Framework para el desarrollo de aplicaciones Java.
- **Spring Security**: Proporciona autenticación y autorización basada en roles.
- **JWT (JSON Web Tokens)**: Para la gestión de sesiones de usuario seguras.
- **Spring Data JPA**: Implementación de acceso a datos para trabajar con bases de datos relacionales.
- **MySQL**: Base de datos utilizada para almacenar la información.
- **AWS S3**: Servicio de almacenamiento en la nube para almacenar imágenes de autos.
- **ModelMapper**: Biblioteca para mapear objetos de un tipo a otro.
- **JUnit** y **Mockito**: Utilizados para realizar pruebas unitarias en el backend.

## Estructura de Seguridad y Autorización

La API utiliza **Spring Security** para controlar el acceso a sus endpoints mediante roles `USER` y `ADMIN`. Los roles asignados a cada endpoint son los siguientes:

### Endpoints Públicos

| Endpoint                  | Descripción                       | Roles Permitidos |
| ------------------------- | --------------------------------- | ---------------- |
| `POST /users/register`    | Registro de un nuevo usuario      | Todos            |
| `GET /categories/**`      | Obtener todas las categorías      | Todos            |
| `GET /characteristics/**` | Obtener todas las características | Todos            |
| `GET /autos/**`           | Obtener todos los autos           | Todos            |

### Endpoints de Usuario

| Endpoint                           | Descripción                                  | Roles Permitidos |
| ---------------------------------- | -------------------------------------------- | ---------------- |
| `POST /users/create`               | Crear un nuevo usuario                       | USER             |
| `GET /users/{id}`                  | Obtener información de un usuario específico | USER             |
| `PUT /users/update/{id}`           | Actualizar información del usuario           | USER, ADMIN      |
| `PUT /users/update/privilege/{id}` | Actualizar privilegios de usuario            | ADMIN            |
| `DELETE /users/delete/{id}`        | Eliminar un usuario                          | ADMIN            |

### Endpoints de Autos

| Endpoint                    | Descripción                                            | Roles Permitidos |
| --------------------------- | ------------------------------------------------------ | ---------------- |
| `POST /autos/register`      | Registrar un nuevo auto                                | ADMIN            |
| `POST /autos/registers3`    | Registrar un auto con imágenes en AWS S3               | ADMIN            |
| `PUT /autos/update/{id}`    | Actualizar información del auto                        | ADMIN            |
| `PUT /autos/updates3/{id}`  | Actualizar información del auto con imágenes en AWS S3 | ADMIN            |
| `DELETE /autos/delete/{id}` | Eliminar un auto                                       | ADMIN            |

### Endpoints de Categorías

| Endpoint                         | Descripción                   | Roles Permitidos |
| -------------------------------- | ----------------------------- | ---------------- |
| `POST /categories/register`      | Registrar una nueva categoría | ADMIN            |
| `PUT /categories/update/{id}`    | Actualizar una categoría      | ADMIN            |
| `DELETE /categories/delete/{id}` | Eliminar una categoría        | ADMIN            |

### Endpoints de Características

| Endpoint                              | Descripción                        | Roles Permitidos |
| ------------------------------------- | ---------------------------------- | ---------------- |
| `POST /characteristics/register`      | Registrar una nueva característica | ADMIN            |
| `PUT /characteristics/update/{id}`    | Actualizar una característica      | ADMIN            |
| `DELETE /characteristics/delete/{id}` | Eliminar una característica        | ADMIN            |

### Endpoints de Envío de Correo

| Endpoint             | Descripción                 | Roles Permitidos |
| -------------------- | --------------------------- | ---------------- |
| `POST /mail/send/**` | Enviar correos electrónicos | Todos            |


## Seguridad
- Este proyecto utiliza Spring Security para la autenticación y autorización. La seguridad se maneja mediante ***JWT (JSON Web Token)***, que proporciona tokens seguros para autenticar usuarios. Los roles disponibles son:

- **USER**: Permite acceso a la visualización de autos, categorías y características.
**ADMIN**: Permite todas las funcionalidades de gestión, incluyendo el registro y actualización de autos, categorías, características, reservas y usuarios.

## Configuración de Seguridad
- La configuración de seguridad se maneja a través de clases de configuración y filtros **JWT**. A continuación se muestra un fragmento de cómo se manejan los endpoints protegidos:

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/users/register", "/mail/send/**").permitAll()
        .antMatchers("/users/**").hasRole("USER")
        .antMatchers("/categories/**", "/characteristics/**").permitAll()
        .antMatchers("/autos/register", "/autos/registers3", "/autos/update/**", "/autos/delete/**").hasRole("ADMIN")
        .anyRequest().authenticated()
        .and()
        .addFilterBefore(new JWTAuthorizationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
}

```

## Envío de Correos Electrónicos
- Para enviar correos electrónicos, el sistema utiliza **Spring Mail**, y los usuarios pueden enviar notificaciones a través del endpoint `/mail/send/**`. Esto permite enviar mensajes sobre las reservas y otras actividades relacionadas con el sistema.

## Dependencias del Proyecto

- Este proyecto utiliza las siguientes dependencias de Maven para Spring Boot:

```xml
<dependencies>
    <!-- Spring Boot Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- Spring Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- MySQL Connector -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- Spring Boot Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <!-- JWT (Json Web Token) -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.12.6</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.12.6</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.12.6</version>
        <scope>runtime</scope>
    </dependency>

    <!-- Spring Boot Mail -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-mail</artifactId>
    </dependency>

    <!-- AWS SDK para S3 -->
    <dependency>
        <groupId>io.awspring.cloud</groupId>
        <artifactId>spring-cloud-starter-aws-messaging</artifactId>
    </dependency>
</dependencies>


```


## Configuración

1. **Base de Datos**: Configura la conexión a MySQL en el archivo `application.properties`.

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/nombre_base_datos
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña

   ```

2. **AWS S3**: Configura las credenciales y el bucket de S3 para almacenar imágenes.
   ```properties
    cloud.aws.credentials.access-key=tu_access_key
    cloud.aws.credentials.secret-key=tu_secret_key
    cloud.aws.s3.bucket=tu_nombre_bucket

   ```

3. **Mail service**: Configura las credenciales y el envio de mail de registro.
   ```properties
    spring.mail.host=tu_host
    spring.mail.port=puerto_host
    spring.mail.username=example@dominio.com
    spring.mail.password=password

   ```

## Ejecución

Para ejecutar el proyecto:

1. Clona el repositorio y navega al directorio.

   ```properties
   git clone https://github.com/tu_usuario/tu_repositorio.git
   cd tu_repositorio

   ```

2. Ejecuta el siguiente comando para iniciar la aplicación:

   ```properties
   mvn spring-boot:run

   ```

3. Si prefieres empaquetar y ejecutar el archivo jar:

   ```properties
   mvn package
   java -jar target/nombre_proyecto.jar
   ```

## Estructura del Proyecto

-  El proyecto se organiza de la siguiente manera:

   ```properties
    src/ ├── main/ │ ├── java/ │ │ ├── com/ │ │ │ └── luxurycars/ │ │ │ ├── controller/ │ │ │ ├── dto/ │ │ │ ├── model/ │ │ │ ├── repository/ │ │ │ ├── security/ │ │ │ ├── service/ │ │ │ └── util/ │ └── resources/ │ ├── application.properties │ └── static/ └── test/ └── java/

## Enlace al API
- Proporciona un enlace para acceder al API desplegado. Si aún no tienes un enlace definitivo, puedes dejar un marcador de posición.


    ```markdown
    El API está disponible en el siguiente enlace:  
    [Acceder al API](https://alluring-enchantment-production.up.railway.app)

    ```
## Contacto
- Información de contacto para consultas o soporte.

    ```markdown
    Para más información o preguntas relacionadas con este proyecto, puedes contactarme en:  

    📧 **Correo:** [Caseli L](mailto:casseli.layza@gmail.com)
    
    ```

## Licencia
- Licencia MIT

    ```markdown
    Este proyecto está licenciado bajo la Licencia MIT.  
    Consulta el archivo [LICENSE](LICENSE) para más detalles.

    ```

## Derechos Reservados
- Sobre los derechos reservados si es aplicable.

    ```markdown
    © 2024 Casseli L CodeCloudNet. Todos los derechos reservados.  
    Este proyecto está protegido por las leyes de derechos de autor y no puede ser reproducido, distribuido ni utilizado sin autorización previa.
    
    ```