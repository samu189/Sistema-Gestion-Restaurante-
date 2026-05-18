 Sistema de Gestión de Restaurante - Arquitectura de Microservicios

 Información del Proyecto
* **Asignatura:** Desarrollo FullStack 1 (DSY1103)
* **Evaluación:** Evaluación Parcial 2 - Encargo con Defensa Técnica
* **Ponderación:** 45% Total (30% Encargo Grupal / 70% Defensa Individual)
* **Patrón de Diseño:** CSR (Controller - Service - Repository/Model)

 Integrantes del Equipo
* Bejamin Quintanilla — *Aportes: Gestión de Base de Datos*
* Samuel Berrios — *Aportes: Lógica de Negocio y Comunicación Inter-Microservicio y Modelos relacionales*
* **Samuel Berrios** — *Aportes: Implementación de Validaciones Robustas (Bean Validation), Estructuración de Respuestas Unificadas (`ApiResponse`) e Integración de Logs Estructurados con SLF4J en Capa Controladora.*

---

 Descripción General y Arquitectura
Este proyecto consiste en un ecosistema distribuido para la gestión integral de un restaurante de alta demanda. La solución ha sido diseñada bajo una **Arquitectura de Microservicios** independientes, donde cada módulo posee su propia responsabilidad funcional y su propio esquema de persistencia relacional.

Los servicios están integrados y registrados en un servidor de descubrimiento **Spring Cloud Netflix Eureka Server**, garantizando la localización dinámica de componentes. La comunicación inter-microservicio en flujos críticos del negocio se realiza de forma síncrona mediante clientes HTTP.

Cumplimiento de Estándares de la Pauta (Checklist Técnico)
* **Patrón CSR Estricto:** Separación absoluta en paquetes `controller` (orquestación), `service` (lógica y transacciones) y `model`/`repository` (persistencia con JPA + Hibernate).
* **Validación de Datos (JSR 380):** Uso exhaustivo de `@Valid`, `@NotBlank`, `@Min`, `@NotNull` y `@FutureOrPresent` en entidades y DTOs para garantizar la integridad referencial y de negocio antes de tocar la base de datos.
* **Trazabilidad con SLF4J:** Implementación de la anotación `@Slf4j` en los puntos clave de los controladores para auditar el ingreso de peticiones HTTP, el flujo de datos y facilitar la depuración y lectura de excepciones en tiempo real.
* **Respuestas REST Consistentes:** Encapsulamiento de retornos en un objeto global uniforme `ApiResponse<T>` junto con el uso estricto de `ResponseEntity` para el manejo semántico de códigos de estado HTTP (`200 OK`, `201 Created`, etc.).

---

Catálogo de los 10 Microservicios Implementados

A continuación se detallan los 10 microservicios obligatorios desarrollados para cubrir de extremo a extremo las reglas del negocio del dominio:

1. **`ms-pedidos` (Corazón del Sistema):** Gestiona la creación de órdenes, asignación de comandas y el estado del consumo. Actúa como cliente remoto conectándose con el servicio de pagos.
2. **`ms-pagos`:** Administra las transacciones financieras del restaurante (boletas, facturas, propinas, pasarelas de pago). Recibe peticiones remotas desde el módulo de pedidos.
3. **`ms-detalle-pedido`:** Maneja la granularidad de los platos e ítems específicos solicitados dentro de cada pedido individual, garantizando consistencia relacional.
4. **`ms-inventario`:** Controla el stock físico de ingredientes y materias primas en bodega, alertando sobre insumos críticos.
5. **`ms-menu` (o Platos):** Catálogo digitalizado de alimentos, bebidas, precios, categorías y disponibilidad en cocina.
6. **`ms-cocina`:** Monitorea las comandas en tiempo real, gestionando los tiempos de preparación por plato y el estado ("En Preparación", "Listo").
7. **`ms-mesas`:** Controla la distribución del salón, ubicaciones (Terraza, Salón Principal, VIP), estados de ocupación (Disponible, Ocupada, Reservada) y capacidades físicas.
8. **`ms-notificaciones`:** Sistema de alertas multicanal (Correo, SMS, Push) para avisar a clientes cuando su mesa o pedido delivery está listo.
9. **`ms-reservas`:** Controla la agenda de clientes a futuro mediante validaciones de fechas presentes/futuras y asignación programada de espacios.
10. **`ms-usuarios`:** Gestión de personal, credenciales seguras mediante uso de DTOs exclusivos y asignación de roles jerárquicos (ADMIN, MESERO, CAJERO, COCINA).

---

Comunicación Inter-Microservicio (Flujo Crítico)
Para dar cumplimiento estricto al indicador **IE 2.4.1**, se implementó un consumo de endpoints remoto síncrono mediante Cliente HTTP:
* **Flujo Origen:** `ms-pedidos` (Emisor) ➡️ **Flujo Destino:** `ms-pagos` (Receptor).
* **Mecánica:** Al confirmarse un pedido, el servicio emite una solicitud REST automática para gatillar el registro de la transacción en el módulo de pagos, garantizando la interoperabilidad sin acoplamiento de bases de datos mediante balanceo guiado por el Servidor de Descubrimiento (Eureka).

---

 Instrucciones de Ejecución en Taller / Laboratorio

Siga estrictamente este orden para levantar el ecosistema completo en el entorno local (IntelliJ IDEA / VS Code):

### 1. Prerrequisitos
* Java Development Kit (JDK) 21 instalado.
* Motor de Base de Datos MySQL activo.
* Configurar las credenciales correctas de base de datos en el archivo `application.yml` de cada microservicio que requiera persistencia.

### 2. Secuencia de Arranque
Es mandatario iniciar la arquitectura en el siguiente orden secuencial para evitar errores de conexión:

1. **Servidor de Descubrimiento (Eureka Server):** Levantar primero el proyecto del servidor y confirmar su correcto inicio ingresando al panel web en `http://localhost:8761`.
2. **Microservicios de Base:** Levantar `ms-pagos` y `ms-usuarios`.
3. **Microservicio de Pedidos:** Levantar `ms-pedidos` (Verificar en la consola mediante los Logs de SLF4J que se autoconecta y reconoce el cliente remoto de pagos).
4. **Resto de Microservicios:** Levantar secuencialmente los 7 proyectos restantes (`ms-menu`, `ms-inventario`, `ms-mesas`, etc.).
5. **Verificación:** Refrescar el dashboard de Eureka (`http://localhost:8761`) y constatar que los 10 servicios figuren en estado **UP** dentro de la lista de instancias registradas.

---

## 🧪 Pruebas de Integración (Postman)
Cada microservicio expone rutas REST semánticas preparadas para ser consumidas. Al importar la colección de Postman, se pueden realizar las pruebas CRUD:
* **GET** `/api/[nombre-servicio]` - Recuperación de listados estructurados en JSON.
* **POST** `/api/[nombre-servicio]` - Envío de Request Body validado por Bean Validation.

*Ejemplo de validación activa auditada por Log:* Si se intenta registrar una mesa con capacidad menor a 1 mediante un `POST` a `/api/mesas`, el controlador capturará la infracción gracias a `@Valid`, registrará la traza con `@Slf4j` en la consola de IntelliJ y retornará un código de error controlado en vez de romper la ejecución del servidor.
