---

# Documentación de API para Evaluación Java

Esta documentación explica los endpoints disponibles en la API de la evaluación para la candidatura de Java, junto con sus métodos HTTP y parámetros.

## Tabla de Contenidos
1. [Información de candidato](#información-de-candidato)
2. [Configuración](#configuración-de-postman)
3. [Mostrar productos con paginado](#mostrar-productos-con-paginado)
4. [Buscar productos por nombre](#buscar-productos-por-nombre)
5. [Buscar producto por ID](#buscar-producto-por-id)
6. [Mostrar todos los productos ordenados por precio ascendente](#mostrar-todos-los-productos-ordenados-por-precio-ascendente)
7. [Mostrar todos los productos ordenados por precio descendente](#mostrar-todos-los-productos-ordenados-por-precio-descendente)
8. [Guardar un nuevo producto](#guardar-un-nuevo-producto)
9. [Modificar los datos de un producto](#modificar-los-datos-de-un-producto)
10. [Borrado de un producto](#borrado-de-un-producto)

---

### Información de Candidato

#### Nombre: Andrés Ezequiel Fabbiano
#### [Curriculum](./CV_Fabbiano_Andres-02-2024.pdf)

---

### Configuración de Postman
**nota: se está usando localhost:8080**

#### [Variable de entorno](./fabbianoAndres.postman_environment.json)
#### [Colección de peticiones](./Fabbiano Andres.postman_collection.json)

## Documentación de la Api

### Mostrar productos con paginado

**Endpoint**: `/api/productos`

**Método HTTP**: `GET`

**Parámetros**:

- `page`: Número de página (opcional, por defecto es 0)
- `size`: Tamaño de la página (opcional, por defecto es 10)
- `sort`: Orden (opcional, por defecto es por ID)

**Respuesta**: Lista paginada de productos

---

### Buscar productos por nombre

**Endpoint**: `/api/productos/nombres/{nombre}`

**Método HTTP**: `GET`

**Parámetros**:

- `nombre`: Nombre del producto a buscar (obligatorio)

**Respuesta**: Lista paginada de productos que coinciden con el nombre

---

### Buscar producto por ID

**Endpoint**: `/api/productos/{id}`

**Método HTTP**: `GET`

**Parámetros**:

- `id`: ID del producto a buscar (obligatorio)

**Respuesta**: Información del producto

---

### Mostrar todos los productos ordenados por precio ascendente

**Endpoint**: `/api/productos/precio/ascendente`

**Método HTTP**: `GET`

**Respuesta**: Lista de productos ordenados por precio ascendente

---

### Mostrar todos los productos ordenados por precio descendente

**Endpoint**: `/api/productos/precio/descendente`

**Método HTTP**: `GET`

**Respuesta**: Lista de productos ordenados por precio descendente

---

### Guardar un nuevo producto

**Endpoint**: `/api/productos`

**Método HTTP**: `POST`

**Cuerpo de la petición**: Datos del nuevo producto

**Respuesta**: Detalles del producto creado

---

### Modificar los datos de un producto

**Endpoint**: `/api/productos/{id}`

**Método HTTP**: `PUT`

**Parámetros**:

- `id`: ID del producto a modificar (obligatorio)

**Cuerpo de la petición**: Datos actualizados del producto

**Respuesta**: Detalles del producto modificado

---

### Borrado de un producto

**Endpoint**: `/api/productos/{id}`

**Método HTTP**: `DELETE`

**Parámetros**:

- `id`: ID del producto a borrar (obligatorio)

**Respuesta**: Respuesta HTTP con estado `204 No Content`

---

