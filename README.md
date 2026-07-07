# 🍽️ Sistema de Gestión de Restaurante - Arquitectura de Microservicios

Este repositorio contiene la solución tecnológica integral para la gestión operativa y comercial de un restaurante de alta demanda, desarrollada bajo una **Arquitectura Orientada a Microservicios** utilizando el ecosistema de **Spring Cloud**. El proyecto ha sido diseñado aplicando principios de alta cohesión, bajo acoplamiento, escalabilidad horizontal, tolerancia a fallos y validación rigurosa de reglas de negocio.

---

## 👥 Integrantes del Equipo (Grupo)
* **Samuel berrios** - Desarrollo Backend / QA & Unit Testing / Documentación
* **benjamin quintanilla** - Desarrollo Backend / DevOps

---

## 🏗️ Resumen de la Arquitectura del Sistema
La solución se compone de un ecosistema distribuido donde cada microservicio encapsula su propio dominio de negocio y base de datos independiente, comunicándose de forma síncrona mediante **OpenFeign** y centralizando el flujo de tráfico a través de un **API Gateway**.

### Componentes de Infraestructura Global:
* **Spring Cloud Gateway:** Enrutador único y centralizado del sistema que gestiona la seguridad y el redireccionamiento mediante configuraciones dinámicas en formato `YAML`.
* **Swagger / OpenAPI 3:** Documentación interactiva e independiente expuesta en cada microservicio para validar contratos y esquemas JSON.

---

## 📦 Lista de Microservicios Implementados (Negocio)
Para dar cumplimiento estricto a la pauta de evaluación (Mínimo 10 microservicios por equipo), el ecosistema está distribuido de la siguiente manera:

1. **`ms-usuarios`**: Gestión de perfiles, autenticación, roles de empleados (garzones, cocineros, administradores) y clientes VIP.
2. **`ms-inventario`**: Control físico de insumos en bodega, stock mínimo de ingredientes y alertas de desabastecimiento.
3. **`ms-pedidos`**: Orquestación y ciclo de vida de los pedidos maestros de las mesas. Se comunica vía Feign con `ms-menu`.
4. **`ms-detalle-pedido`**: Gestión granular de las líneas de comandas, realizando cálculos automáticos de subtotales financieros en base a cantidad y precio unitario.
5. **`ms-cocina`**: Panel operativo para el equipo culinario. Controls el flujo de preparación de comandas y asigna estados automáticos de "PENDIENTE".
6. **`ms-notificaciones`**: Central de alertas del sistema. Despacha confirmaciones operativas registrando estampas de tiempo (`LocalDateTime`) precisas.
7. **`ms-pagos`**: Pasarela transaccional para la liquidación financiera de pedidos, con persistencia local y sincronización remota hacia `ms-pedidos`.
8. **`ms-reservas`**: Módulo de agendamiento anticipado de cubiertos y asignación de zonas (VIP, Terraza, Salón principal).
9. **`ms-mesas`**: Control de disponibilidad física de recursos del restaurante (Libre, Ocupada, Reservada, En Limpieza).
10. **`ms-menu`**: Catálogo maestro de platillos, categorías gastronómicas, descripciones funcionales y precios vigentes.

---

## 🧪 Estrategia de Calidad y Pruebas Unitarias (JUnit 5 & Mockito)
El núcleo de la lógica de negocio se encuentra blindado mediante pruebas unitarias rigurosas, logrando una alta cobertura de código y garantizando la estabilidad ante cambios.

* **Aislamiento de Dependencias:** Uso estricto de `@Mock` y `@InjectMocks` para independizar la capa de servicios de la persistencia real.
* **Simulación Remota Avanzada:** Empleo de `when().thenReturn()` y `doNothing()` para interceptar y emular llamadas HTTP entre microservicios a través de clientes OpenFeign (ej. simulación en `ms-pedidos` y `ms-pagos`).
* **Validación de Reglas de Negocio:** Aserciones robustas (`assertEquals`, `assertNotNull`) que verifican cálculos automáticos de subtotales, inyección automática de marcas de tiempo y transiciones de estados por defecto ("PENDIENTE", "COMPLETADO", "CONFIRMADA").

---

## 🚀 Instrucciones de Ejecución en Entorno Local

### Requisitos Previos:
* Java 17 o Java 21 (JDK instalado y configurado en las variables de entorno).
* Apache Maven 3.8+.
* Motor de Base de Datos compatible (según los perfiles configurados en `application.yml`).
