# Furnistore-CVDS-DOSW-01
FurniStore-CVDS-DOSW-01 — Backend académico en Java 17 + Spring Boot para la tienda de muebles FurniStore. Gestiona productos, pedidos, inventario y entregas. Incluye GitFlow, SOLID, patrones de diseño y calidad con JUnit, JaCoCo, SonarQube y Swagger UI.

# Autor:
- Juan Pablo Nieto Cortes
---
El proyecto se ha configurado con las siguientes tecnologías:

- **Java 17** → Lenguaje base.
- **Spring Boot** → Framework para el desarrollo del backend.
- **JUnit 5 + Mockito** → Framework de testing.
- **JaCoCo** → Generación de reportes de cobertura de pruebas.
- **SonarQube** → Análisis de calidad y mantenibilidad del código.
- **Swagger UI (springdoc-openapi)** → Documentación automática y navegable de la API REST.

---
# Estrategia de ramas (GitFlow)

Para garantizar un flujo de trabajo organizado en equipo, se define la estrategia **GitFlow**:

- **main** → Rama principal, siempre estable y desplegable.
- **develop** → Rama de desarrollo, donde se integran las nuevas funcionalidades antes de pasar a `main`.
- **feature/** → Cada funcionalidad nueva se desarrolla en una rama `feature/nombre-funcionalidad`.
- **bugfix/** → Ramas para corregir errores en desarrollo.
- **release/** → Preparación de una versión antes de pasar a `main`.
- **hotfix/** → Correcciones críticas en producción.

---

# Convenciones de commits

Se seguirá la convención **Conventional Commits**:

- **feat:** → Nueva funcionalidad.
- **fix:** → Corrección de errores.
- **test:** → Añadir o mejorar pruebas.
- **docs:** → Cambios en documentación.
- **refactor:** → Refactorización de código sin cambiar funcionalidad.
- **style:** → Cambios de estilo (formato, nombres, espacios).
- **chore:** → Tareas varias (build, config, dependencias).

---

## Patrones de Diseño Implementados

## 1. Builder Pattern
Es un patrón de creación que ayuda a construir objetos paso a paso, separando cómo se crean de cómo se usan.  
En este caso se aplica porque:
- Los muebles tienen muchos atributos configurables.
- Permite armar los muebles de forma flexible.
- Se pueden crear diferentes variantes fácilmente.
- Es útil cuando un objeto puede tener varias combinaciones de propiedades.

---

## Principios SOLID Aplicados

- **SRP (Responsabilidad Única):**  
  Cada clase hace solo una cosa: el *Builder* construye, el *Director* organiza y el *Mueble* guarda datos.

- **OCP (Abierto/Cerrado):**  
  Se pueden agregar nuevos tipos de muebles sin modificar lo que ya existe.

- **LSP (Sustitución de Liskov):**  
  Los *builders* concretos se pueden cambiar sin problema por la interfaz `MuebleBuilder`.

- **ISP (Segregación de Interfaces):**  
  La interfaz `MuebleBuilder` solo tiene los métodos necesarios para construir muebles.

- **DIP (Inversión de Dependencias):**  
  El *Director* trabaja con la interfaz `MuebleBuilder`, no con implementaciones específicas.

---

---
## diagrama de contexto
El diagrama de contexto establece los límites del sistema y sus interacciones con actores externos, fundamental en las fases de análisis de requisitos del ciclo de vida de desarrollo.
![img.png](docs/uml/diagrama de contexto.png)

## casos de uso 
Fundamento Teórico: Los casos de uso capturan los requisitos funcionales desde la perspectiva del usuario, esencial en la fase de especificación de requisitos del ciclo de vida.
![casos de uso.png](docs/uml/casos%20de%20uso.png)

## diagrama de clases:
Fundamento Teórico: Implementa el patrón Builder de GoF, permitiendo la construcción paso a paso de objetos complejos (muebles), facilitando el mantenimiento y extensibilidad del código.
![Diagrama de clases.png](docs/uml/Diagrama%20de%20clases.png)

## jacoco

![img.png](docs/imagenes/jacoco.png)

## sonarQube

![img.png](docs/imagenes/sonar.png)

## swagger

![img.png](docs/imagenes/img.png)

![img_1.png](docs/imagenes/img_1.png)

---

## 3. Backlog de Producto

### Historias de Usuario

#### HU1 – Registro de Cliente
**Como** cliente, **quiero** registrar mis datos personales en el sistema, **para** poder generar facturas personalizadas y realizar futuras compras sin repetir información.

**Criterios de aceptación:**
- El formulario debe solicitar nombre, identificación, dirección y correo electrónico.
- El sistema valida que el correo no esté repetido.
- Se almacena correctamente el cliente en la base de datos.

---

#### HU2 – Generar Factura
**Como** cliente, **quiero** recibir una factura con el detalle de mis compras y el total con IVA, **para** tener claridad en el costo final.

**Criterios de aceptación:**
- La factura incluye datos del cliente, productos, cantidades y precios unitarios.
- Se calcula el subtotal, IVA y total final.
- El sistema genera un identificador único de factura.

---

#### HU3 – Aplicar Descuentos a la Factura
**Como** cliente, **quiero** que se apliquen descuentos cuando haya promociones, **para** reducir el costo total de mi compra.

**Criterios de aceptación:**
- El sistema permite aplicar un porcentaje de descuento configurable.
- El descuento se refleja en el total de la factura.
- Se mantiene el detalle del cálculo en la factura.

---

#### HU4 – Calcular Costo de Envío
**Como** cliente, **quiero** visualizar el costo de envío asociado a mi compra, **para** conocer el valor total antes de confirmar la orden.

**Criterios de aceptación:**
- El sistema calcula el costo de envío según destino y tipo de producto.
- El valor se suma automáticamente al total de la factura.
- El detalle del costo se muestra en el resumen final.

---

#### HU5 – Consultar Facturas Emitidas
**Como** administrador, **quiero** consultar las facturas generadas por cliente y fecha, **para** llevar un control contable y de ventas.

**Criterios de aceptación:**
- El sistema permite buscar por ID de cliente o rango de fechas.
- Se muestran los totales y detalles de cada factura.
- La información se obtiene correctamente desde la base de datos.

---

## 4. Planeación del Sprint

### Tareas Técnicas y Ramas Asociadas

| Nº | Tarea Técnica | Rama Git | Responsable | Estimación (horas) |
|----|----------------|-----------|--------------|---------------------|
| 1 | Crear entidades `Factura`, `Cliente` y `ItemFactura` | feature/modelos-factura | Juan Pablo Nieto | 4 |
| 2 | Implementar `FacturaDecorator` y decoradores (`IVA`, `Descuento`, `Envío`) | feature/decoradores-factura | Juan Pablo Nieto | 6 |
| 3 | Desarrollar `FacturaService` para la lógica de generación y cálculo total | feature/servicio-factura | Juan Pablo Nieto | 5 |
| 4 | Crear `FacturaController` con endpoint para generar factura | feature/controlador-factura | Juan Pablo Nieto | 3 |
| 5 | Actualizar diagramas (clases y casos de uso) | feature/diagramas-actualizados | Juan Pablo Nieto | 3 |
| 6 | Documentar backlog y planeación en `README.md` | feature/documentacion | Juan Pablo Nieto | 2 |
