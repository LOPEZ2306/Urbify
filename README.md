# 💼 Visualia | Gestión de Ventas Segura y Controlada  

**Visualia** es una aplicación web diseñada para optimizar la gestión de ventas bajo un modelo jerárquico de usuarios.  
Garantiza la **seguridad, trazabilidad y control de modificaciones**, asegurando que todas las operaciones queden registradas y auditadas.  
El sistema está totalmente adaptado para dispositivos móviles, facilitando su uso desde cualquier lugar.  

---

## 🚀 Características  

🔹 **SuperAdmin**  
✔️ Control total del sistema.  
✔️ Creación, edición y eliminación de administradores y vendedores.  

🔹 **Administrador (Admin)**  
✔️ Aprueba o rechaza solicitudes de edición o eliminación de ventas.  
✔️ Genera reportes PDF antes de eliminar cualquier registro.  
✔️ Gestiona alertas, configuraciones y auditorías del sistema.  

🔹 **Vendedor (Seller)**  
✔️ Registro de nuevas ventas y abonos.  
✔️ **No puede eliminar ventas.**  
✔️ Puede solicitar ediciones que serán aprobadas por un administrador.  
✔️ Visualización de alertas de pagos vencidos y estados de venta.  

---

## 💰 Estructura de una Venta  

Cada venta contiene la siguiente información:  

- 💵 **Valor total de la venta**  
- 💳 **Abonos** 
- ❌ **Cancelación de ventas**  
- 👤 **Nombre completo del cliente**  
- 🪪 **Cédula**  
- 🏠 **Dirección**  
- 📦 **Productos vendidos**  
- 📅 **Fecha de creación**  
- 🔁 **Estado actual del proceso (en curso, abonada, cancelada, etc.)**

---

## ⚙️ Funcionalidades Clave  

✔️ Filtrado de ventas por **estado o mes**, sin depender de la fecha de creación.  
✔️ **Alerta automática** de pagos vencidos.  
✔️ Comparación entre **fecha local y global** para evitar inconsistencias.  
✔️ Retención de información durante **2 meses sin movimiento**.  
✔️ Eliminación solo tras **aprobación del Admin**, con **registro PDF** como respaldo.  
✔️ **Diseño responsive** optimizado para uso móvil.  

---

## 🛠️ Tecnologías Utilizadas  

🔹 **Spring Boot & Spring Security** – Backend y control de roles.  
🔹 **Spring Data JPA** – Persistencia y manejo de base de datos.  
🔹 **Spring Mail** – Envío de notificaciones y alertas.  
🔹 **Thymeleaf** – Motor de plantillas para vistas dinámicas.  
🔹 **Spring Boot DevTools** – Herramientas para pruebas y desarrollo.  

---

**Visualia** — “Seguridad, control y trazabilidad en cada venta.”  
💻 Desarrollado con ❤️ utilizando **Spring Boot + Thymeleaf**.
