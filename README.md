# Reservas de Salas App

## Descripción
Aplicación Android que permite a los usuarios registrar cuentas, gestionar reservas de salas, consultar reglamentos, visualizar ubicaciones en un mapa y recibir notificaciones de recordatorio.  

**Tecnologías principales:**
- Android Studio (Kotlin/Java)
- SQLite (gestión de reservas)
- SharedPreferences (manejo de sesión)
- Google Maps API
- Material Design y RecyclerView

---

## Funcionalidades

- **Autenticación**
  - Registro y login con validación de campos.
  - Sesión persistente con SharedPreferences.
  
- **Perfil de usuario**
  - Visualización y edición de datos básicos: nombre, correo, teléfono.
  
- **Reservas**
  - Crear, listar y cancelar reservas.
  - Cada usuario solo ve sus propias reservas.
  - CRUD completo implementado con SQLite.
  
- **Mapa**
  - Integración con Google Maps para mostrar salas y ubicación de la empresa.
  
- **Reglamento**
  - Consulta de reglamento mediante WebView (PDF o URL).
  
- **Navegación**
  - Navigation Drawer con acceso rápido a todas las pantallas.
  - AppBar dinámico según pantalla.
  
- **Seguridad y permisos**
  - Solicitud de permisos de ubicación, almacenamiento e internet en tiempo de ejecución.

---


## Instalación

1. Clonar este repositorio:

```bash
git clone https://github.com/Bryan12sd/reservasApp.git
```
## Instalación

1. Abrir el proyecto en **Android Studio**.
2. Configurar un emulador o conectar un dispositivo físico.
3. Compilar y ejecutar la app:

```bash
./gradlew build
```
4. Instalar el apk generado.
---
## Autor

**Bryan Cordero**  
Proyecto desarrollado como parte de la asignatura de Aplicaciones Móviles.

