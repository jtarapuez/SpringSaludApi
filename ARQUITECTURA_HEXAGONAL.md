# Induccion: Arquitectura Hexagonal en BaseSpringApi

## 1. Objetivo del documento

Este documento explica como esta organizado el proyecto `BaseSpringApi` y como se adapta a una arquitectura hexagonal, tambien conocida como arquitectura de puertos y adaptadores.

La idea principal de esta arquitectura es separar la logica central del sistema de los detalles tecnicos externos, como HTTP, Spring MVC, JPA, PostgreSQL, archivos JSON o Swagger.

En este proyecto, la API esta centrada en la consulta de unidades medicas del IESS.

## 2. Arquitectura hexagonal en general

La arquitectura hexagonal organiza una aplicacion en tres zonas principales:

- Dominio: representa los conceptos principales del negocio.
- Aplicacion: contiene los casos de uso y contratos que necesita el negocio.
- Infraestructura: contiene los mecanismos externos para entrar o salir del sistema.

Tambien se la llama arquitectura de puertos y adaptadores porque:

- Los puertos definen contratos.
- Los adaptadores implementan esos contratos o conectan el sistema con el exterior.

El objetivo es que el centro de la aplicacion no dependa directamente de tecnologias externas.

Por ejemplo, el caso de uso no deberia saber si los datos vienen de PostgreSQL, de un archivo JSON o de otro servicio. Solo deberia depender de un contrato.

## 3. Estructura general del proyecto

La estructura principal de `src` es:

```text
src
├── main
│   ├── java
│   │   └── iess/gen/basespringapi
│   │       ├── BaseSpringApiApplication.java
│   │       ├── model
│   │       ├── application
│   │       └── infrastructure
│   └── resources
│       ├── application.yaml
│       ├── application-postgres.yaml
│       ├── application-mock.yaml
│       ├── db
│       └── data
└── test
    ├── java
    └── resources
```

El paquete base es:

```text
iess.gen.basespringapi
```

Desde este paquete, Spring Boot escanea los componentes de la aplicacion.

## 4. Clase principal de arranque

Archivo:

```text
src/main/java/iess/gen/basespringapi/BaseSpringApiApplication.java
```

Esta clase contiene el metodo `main` y la anotacion `@SpringBootApplication`.

Su responsabilidad es iniciar la aplicacion Spring Boot:

```text
SpringApplication.run(BaseSpringApiApplication.class, args)
```

No contiene logica de negocio. Solo sirve como punto de entrada tecnico.

## 5. Capa de dominio

Ubicacion:

```text
src/main/java/iess/gen/basespringapi/model
```

Archivo principal:

```text
UnidadMedica.java
```

Esta clase representa el concepto central del sistema: una unidad medica.

Contiene atributos como:

- `id`
- `nombre`
- `nivel`
- `latitud`
- `longitud`
- `descripcion`
- `telefono`
- `sitioWeb`
- `siglas`
- `direccion`
- `provincia`
- `status`
- `createdBy`
- `createdAt`
- `updatedBy`
- `updatedAt`
- `deletedBy`
- `deletedAt`

Esta capa es considerada el nucleo del negocio.

Una caracteristica importante es que `UnidadMedica` no esta anotada como entidad JPA ni como DTO REST. Es una clase de dominio pura, independiente de la base de datos y de los controladores HTTP.

## 6. Capa de aplicacion

Ubicacion:

```text
src/main/java/iess/gen/basespringapi/application
```

Esta capa contiene los casos de uso y los puertos.

Su responsabilidad es responder a la pregunta:

```text
Que puede hacer el sistema?
```

En este proyecto, el sistema puede:

- Listar unidades medicas activas.
- Buscar unidades por texto.
- Filtrar unidades por provincia.
- Filtrar unidades por nivel.
- Buscar una unidad por ID.
- Buscar una unidad por siglas.
- Agrupar unidades por provincia.

## 7. Casos de uso

Ubicacion:

```text
src/main/java/iess/gen/basespringapi/application/usecase
```

Archivo:

```text
UnidadMedicaUseCase.java
```

Este caso de uso concentra la logica de aplicacion relacionada con unidades medicas.

Metodos principales:

```text
obtenerUnidadesAgrupadas()
buscarUnidades(String termino, String provincia, Integer nivel)
buscarPorId(UUID id)
buscarPorSiglas(String siglas)
```

Tambien contiene la logica para agrupar las unidades por provincia y ordenar la informacion antes de devolverla.

El caso de uso depende de:

```text
UnidadMedicaRepositoryPort
UnidadMedicaMapper
```

Lo mas importante es que no depende directamente de PostgreSQL, JPA ni del archivo JSON.

## 8. Puertos

Ubicacion:

```text
src/main/java/iess/gen/basespringapi/application/port
```

Archivo:

```text
UnidadMedicaRepositoryPort.java
```

Este puerto define el contrato de persistencia que necesita el caso de uso.

Metodos definidos:

```text
findAllActive()
findById(UUID id)
findBySiglas(String siglas)
search(String termino, String provincia, Integer nivel)
save(UnidadMedica unidadMedica)
```

El puerto no indica como se buscan o guardan los datos. Solo define que operaciones necesita la aplicacion.

Esto permite tener diferentes implementaciones:

- Una implementacion con PostgreSQL.
- Una implementacion mock basada en JSON.

Ambas pueden ser usadas por el mismo caso de uso sin cambiar la logica central.

## 9. Capa de infraestructura

Ubicacion:

```text
src/main/java/iess/gen/basespringapi/infrastructure
```

Esta capa contiene los detalles tecnicos externos al nucleo:

```text
infrastructure
├── config
├── controller
├── mapper
├── persistence
└── util
```

Aqui viven los componentes que dependen de Spring MVC, JPA, PostgreSQL, JSON, Swagger y configuraciones tecnicas.

En arquitectura hexagonal, esta capa esta por fuera del nucleo de la aplicacion.

## 10. Adaptadores de entrada

Los adaptadores de entrada son los componentes que permiten que algo externo llame a la aplicacion.

En este proyecto, el adaptador de entrada principal es REST.

Ubicacion:

```text
src/main/java/iess/gen/basespringapi/infrastructure/controller
```

Archivos principales:

```text
UnidadMedicaController.java
HealthController.java
RootController.java
```

### UnidadMedicaController

Este controlador expone los endpoints principales de unidades medicas:

```text
GET /api/unidades-medicas
GET /api/unidades-medicas?provincia=PICHINCHA
GET /api/unidades-medicas?nivel=2
GET /api/unidades-medicas?q=hospital
GET /api/unidades-medicas/buscar?q={termino}
GET /api/unidades-medicas/siglas/{siglas}
GET /api/unidades-medicas/{id}
```

Su responsabilidad es:

- Recibir peticiones HTTP.
- Leer parametros de entrada.
- Llamar al caso de uso.
- Retornar una respuesta HTTP.

No deberia contener reglas fuertes de negocio. Esa responsabilidad pertenece al caso de uso.

### HealthController

Expone:

```text
GET /api/health
```

Sirve para verificar si la aplicacion esta activa.

### RootController

Expone informacion basica de la API y rutas disponibles.

## 11. DTOs

Ubicacion:

```text
src/main/java/iess/gen/basespringapi/infrastructure/controller/dto
```

Clases principales:

```text
UnidadMedicaRequest
UnidadMedicaResponse
UnidadMedicaPublicResponse
ProvinciaUnidadesResponse
ProvinciaUnidadesPublicResponse
ErrorResponse
```

Los DTOs se usan para controlar los datos que entran y salen por REST.

Esto evita exponer directamente el modelo de dominio o la entidad JPA.

Separacion aplicada:

```text
HTTP request/response -> DTO
Negocio -> UnidadMedica
Base de datos -> Entity JPA
```

## 12. Adaptadores de salida

Los adaptadores de salida son los componentes que conectan el caso de uso con sistemas externos.

En este proyecto existen dos adaptadores de salida para el mismo puerto:

```text
UnidadMedicaMockRepository.java
UnidadMedicaPostgresRepository.java
```

Ambos implementan:

```text
UnidadMedicaRepositoryPort
```

Esto permite cambiar la fuente de datos sin cambiar el caso de uso.

## 13. Adaptador mock

Ubicacion:

```text
src/main/java/iess/gen/basespringapi/infrastructure/persistence/mock
```

Archivo:

```text
UnidadMedicaMockRepository.java
```

Este adaptador se activa con el perfil:

```text
mock
```

Carga los datos desde:

```text
src/main/resources/data/unidades-medicas.json
```

Usa un almacenamiento en memoria basado en:

```text
ConcurrentHashMap
```

Este adaptador es util para pruebas locales, desarrollo o escenarios donde no se desea conectar a PostgreSQL.

## 14. Adaptador PostgreSQL

Ubicacion:

```text
src/main/java/iess/gen/basespringapi/infrastructure/persistence/jpa
```

Archivos principales:

```text
UnidadMedicaPostgresRepository.java
UnidadMedicaJpaRepository.java
UnidadMedicaEntity.java
ProvinciaEntity.java
ProvinciaJpaRepository.java
```

Este adaptador se activa con el perfil:

```text
postgres
```

Su responsabilidad es conectar la aplicacion con la base de datos PostgreSQL usando Spring Data JPA.

La entidad `UnidadMedicaEntity` mapea contra:

```text
schema: salud
table: unidades_medicas
```

La clase `UnidadMedicaPostgresRepository` implementa el puerto `UnidadMedicaRepositoryPort`, por eso puede ser usada por el caso de uso sin que este conozca los detalles de JPA.

## 15. Mapper

Ubicacion:

```text
src/main/java/iess/gen/basespringapi/infrastructure/mapper
```

Archivo:

```text
UnidadMedicaMapper.java
```

El mapper convierte datos entre las distintas representaciones:

```text
UnidadMedicaEntity -> UnidadMedica
UnidadMedica -> UnidadMedicaEntity
UnidadMedicaRequest -> UnidadMedica
UnidadMedica -> UnidadMedicaResponse
UnidadMedica -> UnidadMedicaPublicResponse
```

Esta clase ayuda a mantener separadas las capas.

Sin el mapper, el controller podria terminar usando entidades JPA directamente, o el caso de uso podria terminar dependiendo de detalles de base de datos.

## 16. Configuracion

Ubicacion:

```text
src/main/java/iess/gen/basespringapi/infrastructure/config
```

Archivos principales:

```text
AppProperties.java
CorsConfig.java
OpenApiConfig.java
```

Responsabilidades:

- Leer propiedades de configuracion.
- Configurar CORS.
- Configurar Swagger/OpenAPI.

Los archivos YAML estan en:

```text
src/main/resources
```

Archivos:

```text
application.yaml
application-postgres.yaml
application-mock.yaml
```

### application.yaml

Configura valores generales:

```text
server.port: 8080
server.servlet.context-path: /api
spring.application.name: BaseSpringApi
spring.profiles.active: postgres
app.data.json-path: classpath:data/unidades-medicas.json
swagger
cors
```

### application-postgres.yaml

Configura la conexion PostgreSQL:

```text
DB_HOST
DB_PORT
DB_NAME
DB_USER
DB_PASSWORD
schema salud
hibernate ddl-auto validate
```

### application-mock.yaml

Configura el perfil mock y desactiva la autoconfiguracion de base de datos.

## 17. Scripts SQL y datos

Ubicacion:

```text
src/main/resources/db
```

Archivos:

```text
01_create_database.sql
02_schema.sql
03_seed_provincias.sql
04_seed_unidades_medicas.sql
README_EJECUCION.txt
```

Estos archivos preparan la base de datos, el esquema y los datos iniciales.

Ubicacion de datos mock:

```text
src/main/resources/data/unidades-medicas.json
```

Este archivo permite levantar la aplicacion con datos en memoria usando el perfil `mock`.

## 18. Manejo de errores

Ubicacion:

```text
src/main/java/iess/gen/basespringapi/infrastructure/controller/advice
```

Archivo:

```text
GlobalExceptionHandler.java
```

Centraliza el manejo de excepciones REST.

Casos manejados:

- `IllegalArgumentException`: devuelve 404.
- `NoResourceFoundException`: devuelve 404 para rutas inexistentes.
- `MethodArgumentTypeMismatchException`: devuelve 400 para parametros invalidos.
- `Exception`: devuelve 500 para errores internos.

La respuesta de error usa:

```text
ErrorResponse
```

## 19. Flujo general de una peticion

Flujo conceptual:

```text
Cliente HTTP
   |
   v
Controller
   |
   v
UseCase
   |
   v
RepositoryPort
   |
   v
Repository Adapter
   |
   v
JSON o PostgreSQL
```

En terminos del proyecto:

```text
UnidadMedicaController
   |
   v
UnidadMedicaUseCase
   |
   v
UnidadMedicaRepositoryPort
   |
   v
UnidadMedicaMockRepository o UnidadMedicaPostgresRepository
```

## 20. Ejemplo real de busqueda

Peticion:

```text
GET /api/unidades-medicas/buscar?q=hospital
```

Flujo con PostgreSQL:

```text
UnidadMedicaController.buscar()
   |
   v
UnidadMedicaUseCase.buscarUnidades()
   |
   v
UnidadMedicaRepositoryPort.search()
   |
   v
UnidadMedicaPostgresRepository.search()
   |
   v
UnidadMedicaJpaRepository.searchActive()
   |
   v
PostgreSQL
```

Flujo con mock:

```text
UnidadMedicaController.buscar()
   |
   v
UnidadMedicaUseCase.buscarUnidades()
   |
   v
UnidadMedicaRepositoryPort.search()
   |
   v
UnidadMedicaMockRepository.search()
   |
   v
data/unidades-medicas.json
```

El caso de uso no cambia. Solo cambia el adaptador activo segun el perfil.

## 21. Relacion entre capas del proyecto

| Capa hexagonal | Ubicacion en el proyecto | Responsabilidad |
| --- | --- | --- |
| Dominio | `model` | Representar el modelo de negocio |
| Aplicacion | `application/usecase` | Ejecutar reglas y casos de uso |
| Puerto de salida | `application/port` | Definir contratos de persistencia |
| Adaptador de entrada | `infrastructure/controller` | Recibir llamadas HTTP |
| DTOs | `infrastructure/controller/dto` | Modelar entrada y salida REST |
| Adaptador de salida mock | `infrastructure/persistence/mock` | Leer datos desde JSON en memoria |
| Adaptador de salida PostgreSQL | `infrastructure/persistence/jpa` | Leer y guardar datos con JPA |
| Mapper | `infrastructure/mapper` | Convertir entre DTO, dominio y entidades |
| Configuracion | `infrastructure/config` | Configurar CORS, Swagger y propiedades |
| Recursos | `src/main/resources` | YAML, SQL y datos JSON |

## 22. Beneficio principal en este proyecto

El mayor beneficio de esta estructura es que el caso de uso trabaja contra una abstraccion:

```text
UnidadMedicaRepositoryPort
```

Por eso puede funcionar con:

```text
UnidadMedicaMockRepository
```

o con:

```text
UnidadMedicaPostgresRepository
```

sin cambiar la logica principal.

Esto facilita:

- Pruebas locales.
- Cambio de fuente de datos.
- Separacion entre negocio e infraestructura.
- Mantenimiento del codigo.
- Escalabilidad para agregar nuevos adaptadores en el futuro.

## 23. Resumen final

`BaseSpringApi` aplica una arquitectura hexagonal simple y entendible.

La aplicacion esta separada en:

```text
model
application
infrastructure
```

El dominio representa el negocio, la aplicacion contiene los casos de uso y los puertos, y la infraestructura contiene los detalles externos como REST, JPA, PostgreSQL, JSON, Swagger y configuracion.

La pieza central de desacoplamiento es:

```text
UnidadMedicaRepositoryPort
```

Gracias a ese puerto, el caso de uso no necesita saber si los datos vienen desde una base PostgreSQL o desde un archivo JSON mock.

Esa separacion es el punto mas importante de la arquitectura hexagonal dentro de este proyecto.
