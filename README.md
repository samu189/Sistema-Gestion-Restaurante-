# 🍽️ Sistema de Gestión de Restaurante - Arquitectura de Microservicios

Este proyecto consiste en una arquitectura distribuida basada en microservicios independientes para la gestión integral de un restaurante. El sistema implementa comunicación orientada a servicios, persistencia aislada, documentación técnica automatizada y pruebas unitarias para el núcleo del negocio.

## 👥 Integrantes del Equipo
* Samuel Berrios
* Benjamin Quintanilla

---

## 🚀 Ecosistema de Microservicios

A continuación, se detallan los 10 microservicios implementados, sus responsabilidades principales, las tecnologías clave aplicadas según la rúbrica y sus puertos de acceso local:

| Microservicio | Puerto | Componentes Destacados / Tecnologías | Enlace Swagger (Local) |
| :--- | :---: | :--- | :--- |
| **eureka-server** | `8761` | Servidor de Descubrimiento (Spring Cloud Netflix Eureka) | N/A |
| **api-gateway** | `8090` | Centralización de Rutas y Filtros de Acceso | N/A |
| **ms-mesas** | `8092` | Gestión de Capacidad y Estados / **Mockito Tests** | [Ver Swagger](http://localhost:8092/swagger-ui.html) |
| **ms-menu** | `8085` | Catálogo de Platos y Precios / **Mockito Tests** | [Ver Swagger](http://localhost:8085/swagger-ui.html) |
| **ms-pedidos** | `8082` | Orquestación / **Mockito Tests (Feign Client + Mockito)** | [Ver Swagger](http://localhost:8082/swagger-ui.html) |
| **ms-cocina** | `8088` | Cola de Preparación de Pedidos / Soporte **HATEOAS** | [Ver Swagger](http://localhost:8088/swagger-ui.html) |
| **ms-detalle-pedido**| `8086` | Desglose e Ítems de Comandas / Soporte **HATEOAS** | [Ver Swagger](http://localhost:8086/swagger-ui.html) |
| **ms-inventario** | `8083` | Control de Stock e Insumos / Soporte **HATEOAS** | [Ver Swagger](http://localhost:8083/swagger-ui.html) |
| **ms-notificaciones**| `8091` | Alertas de Estado al Cliente / Soporte **HATEOAS** | [Ver Swagger](http://localhost:8091/swagger-ui.html) |
| **ms-pagos** | `8089` | Procesamiento de Boletas y Transacciones / Soporte **HATEOAS** | [Ver Swagger](http://localhost:8089/swagger-ui.html) |
| **ms-reservas** | `8087` | Agendamiento Anticipado / Soporte **HATEOAS** | [Ver Swagger](http://localhost:8087/swagger-ui.html) |
| **ms-usuarios** | `8081` | Gestión de Roles y Autenticación / Soporte **HATEOAS** | [Ver Swagger](http://localhost:8081/swagger-ui.html) |

---

## 🧪 Validación y Calidad: Pruebas Unitarias

El núcleo de las reglas de negocio (`ms-mesas`, `ms-menu` y `ms-pedidos`) se encuentra completamente blindado mediante pruebas unitarias que validan el comportamiento del dominio bajo el patrón **Given-When-Then**:
* **Aislamiento Eficaz:** Uso exhaustivo de `@Mock` y `@InjectMocks` (Mockito) para simular la capa de persistencia sin alterar datos reales.
* **Simulación Inter-Servicios:** El microservicio `ms-pedidos` simula las llamadas remotas exitosas/fallidas de su cliente declarativo Feign (`MenuFeignClient`) aislando el test de caídas de red externas.
* **Cobertura:** Cumplimiento de la cobertura mínima exigida sobre las funciones críticas de negocio.

---

## 📦 Instrucciones de Ejecución

### Requisitos Previos
* Java JDK 21 instalado.
* Motor de Base de Datos operativo (XAMPP / MySQL).

### Ejecución Local Tradicional (IDE)
1. Iniciar en primer lugar el proyecto **eureka-server**.
2. Levantar el ecosistema de microservicios de negocio y soporte.
3. Iniciar el componente **api-gateway** para habilitar el enrutamiento centralizado.

### Ejecución Local Contenedorizada (Docker)
1. Compilar los paquetes ejecutables ejecutando `.\mvnw.cmd clean package` en la raíz de cada servicio.
2. Desde la raíz principal del proyecto donde reside el archivo `docker-compose.yml`, ejecutar en consola:
```bash
   docker compose build
   docker compose up -d
