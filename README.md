# API Rest User

Esta API proporciona endpoints para autenticación y gestión de usuarios utilizando Spring Boot y JWT.

## Configuración

- **Puerto**: 8080
- **Base URL**: `http://localhost:8080`
- **Autenticación**: JWT Token

## Flujo de Trabajo Típico

### 1. Obtener token de autenticación:
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "password"
  }'
```

### 2. Usar el token obtenido en paso 1, para crear un usuario:
```bash
curl --location 'http://localhost:8080/users' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc1MTczNDI4MCwiZXhwIjoxNzUxODIwNjgwfQ.en0WyxbUNblXAnvhBkCDmQ-zF7MAZTItuX0CSBc0My4' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Juan Pérez",
    "email": "juan.perez@bci.cl",
    "password": "Hunter2$",
    "phones": [
        {
            "number": "1234567",
            "citycode": "1",
            "contrycode": "57"
        },
        {
            "number": "2223",
            "citycode": "4",
            "contrycode": "55"
        }
    ]
}'
```

### 3. Usar el token obtenido en paso 1, para obtener todos los usuarios:
```bash
curl --location 'http://localhost:8080/users' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc1MTczNDI4MCwiZXhwIjoxNzUxODIwNjgwfQ.en0WyxbUNblXAnvhBkCDmQ-zF7MAZTItuX0CSBc0My4' \
--header 'Content-Type: application/json' 
```

## Endpoints Disponibles

### 1. Autenticación

#### Login
Autentica un usuario y devuelve un token JWT.

**Endpoint**: `POST /auth/login`

**Headers**:
```
Content-Type: application/json
```

**Body**:
```json
{
  "username": "admin",
  "password": "password"
}
```

**Ejemplo con curl**:
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "password"
  }'
```

**Respuesta exitosa**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "username": "admin",
  "expiresAt": "2025-07-06T12:55:10",
  "message": "Login successful"
}
```

#### Logout
Cierra la sesión del usuario.

**Endpoint**: `POST /auth/logout`

**Headers**:
```
Authorization: Bearer <token>
```

**Ejemplo con curl**:
```bash
curl -X POST http://localhost:8080/auth/logout \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

#### Validar Token
Valida si un token JWT es válido y no ha expirado.

**Endpoint**: `GET /auth/validate`

**Headers**:
```
Authorization: Bearer <token>
```

**Ejemplo con curl**:
```bash
curl -X GET http://localhost:8080/auth/validate \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

### 2. Gestión de Usuarios

#### Obtener todos los usuarios
Obtiene la lista de todos los usuarios.

**Endpoint**: `GET /users`

**Ejemplo con curl**:
```bash
curl -X GET http://localhost:8080/users \
  -H "Content-Type: application/json"
```

#### Crear usuario
Crea un nuevo usuario en el sistema.

**Endpoint**: `POST /users`

**Headers**:
```
Content-Type: application/json
Authorization: Bearer <token>
```

**Body** (ejemplo):
```json
{
  "name": "Juan Pérez",
  "email": "juan.perez@dominio.cl",
  "password": "MiPassword123$",
  "phones": [
    {
      "number": "123456789",
      "citycode": "1",
      "countrycode": "57"
    }
  ]
}
```

**Ejemplo con curl**:
```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -d '{
    "name": "Juan Pérez",
    "email": "juan.perez@ejemplo.cl",
    "password": "MiPassword123$",
    "phones": [
      {
        "number": "123456789",
        "citycode": "1",
        "countrycode": "57"
      }
    ]
  }'
```

**Respuesta exitosa**:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Juan Pérez",
  "email": "juan.perez@ejemplo.cl",
  "created": "2025-07-05T15:30:00",
  "modified": "2025-07-05T15:30:00",
  "last_login": "2025-07-05T15:30:00",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "isactive": true,
  "phones": [
    {
      "number": "123456789",
      "citycode": "1",
      "countrycode": "57"
    }
  ]
}
```

## Códigos de Estado HTTP

- **200 OK**: Operación exitosa
- **201 Created**: Usuario creado exitosamente
- **401 Unauthorized**: Credenciales inválidas o token expirado
- **500 Internal Server Error**: Error interno del servidor

## Notas Importantes

1. **Autenticación**: Los endpoints de usuarios requieren un token JWT válido en el header `Authorization`.
2. **Formato de Token**: El token debe enviarse con el prefijo `Bearer ` en el header Authorization.
3. **Validación**: Todos los campos de entrada son validados. Asegúrate de enviar los datos en el formato correcto.
4. **Logs**: La aplicación registra todos los intentos de login y operaciones importantes.

## Dependencias

- Spring Boot
- Spring Security
- JWT (JSON Web Tokens)
- Lombok
- Jakarta Validation

## Swagger

- **URL**: `http://localhost:8080/swagger-ui/index.html`