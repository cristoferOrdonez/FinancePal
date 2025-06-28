# FinancePal - Tu Gestor Financiero Personal en Android

## 🚀 Descripción del Proyecto

**FinancePal** es una aplicación móvil Android desarrollada en **Java** que ofrece una solución sencilla e intuitiva para la gestión financiera personal. Diseñada para ser un punto de partida accesible, la aplicación permite a los usuarios registrarse, autenticarse de forma segura y visualizar de manera clara su balance actual, historial de movimientos y datos personales. Su interfaz amigable y fácil de navegar la convierte en un excelente activo para portafolios de desarrollo móvil, así como una base sólida para proyectos académicos o personales.

## ✨ Características Destacadas

* **Registro de Usuarios Simplificado:** proceso intuitivo para la creación de nuevas cuentas de usuario con información básica.
* **Autenticación Segura:** sistema de inicio de sesión basado en correo electrónico y contraseña para proteger el acceso a la información personal.
* **Balance Financiero en Tiempo Real:** visualización clara y concisa del balance económico actual del usuario.
* **Historial de Movimientos Detallado:** acceso a un registro histórico de transacciones o balances previos, permitiendo un seguimiento eficaz de las finanzas.
* **Gestión de Perfil Personal:** funcionalidades para consultar y editar la información personal del usuario directamente desde la aplicación.
* **Navegación Intuitiva (UI/UX):** interfaz de usuario diseñada para facilitar el acceso rápido entre las secciones de balance, historial y perfil personal mediante botones accesibles.
* **Persistencia de Datos Local:** almacenamiento eficiente de la información de los usuarios en archivos de texto planos dentro del almacenamiento interno del dispositivo, garantizando la disponibilidad de los datos sin necesidad de conexión a internet.

## 🛠️ Tecnologías Utilizadas

* **Lenguaje de Programación:** Java
* **Framework:** Android SDK
* **Gestor de Dependencias:** Gradle (configuración con Kotlin DSL: `build.gradle.kts`, `settings.gradle.kts`)
* **Almacenamiento de Datos:** archivos de texto en el almacenamiento interno de Android (para una demostración sencilla de persistencia local).
* **Entorno de Desarrollo Integrado (IDE):** Android Studio


## 📸 Capturas de Pantalla
<img src="https://github.com/user-attachments/assets/475ade1e-06b6-4e2a-bc89-1a930b19f53b" width="30%">
<img src="https://github.com/user-attachments/assets/83390132-4657-4ac9-8a1b-87ef5f31e205" width="30%">
<img src="https://github.com/user-attachments/assets/e45787da-7edb-4f5a-953c-baf447ac6b2a" width="30%">


## ⚙️ Instalación y Ejecución

### Prerrequisitos

Para compilar y ejecutar FinancePal, asegúrate de tener instalados los siguientes componentes:

* **Java Development Kit (JDK):** versión 8 o superior.
* **Android Studio:** recomendado para una experiencia de desarrollo óptima.
* Un **Emulador de Android** configurado o un **Dispositivo Físico Android** conectado para la ejecución de la aplicación.

### Pasos para Compilar y Ejecutar

1.  **Clonar el Repositorio:**
    ```sh
    git clone https://[https://github.com/cristoferOrdonez/FinancePal.git](https://github.com/cristoferOrdonez/FinancePal.git)
    ```
2.  **Abrir el Proyecto en Android Studio:**
    navega hasta la carpeta del proyecto clonado y ábrelo directamente con Android Studio.
3.  **Sincronizar y Compilar el Proyecto:**
    utiliza la opción 'Sync Project with Gradle Files' en Android Studio para resolver todas las dependencias y compilar el código.
4.  **Ejecutar la Aplicación:**
    selecciona un emulador o un dispositivo físico y ejecuta la aplicación.

### Notas Importantes

* La aplicación gestiona los datos de los usuarios en un archivo local llamado `InfoUsuariosFinancePal.txt`, ubicado en el almacenamiento interno de la aplicación.
* Para restablecer o limpiar los datos almacenados, puedes eliminar manualmente este archivo a través de las opciones de almacenamiento de la aplicación en la configuración de tu dispositivo Android o emulador.
* La arquitectura y el diseño del proyecto están pensados para demostrar habilidades en desarrollo móvil nativo Java y en la gestión básica de datos persistentes.

## 📂 Estructura del Proyecto

```
.
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/financepal/
│   │       └── res/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew / gradlew.bat
└── .gitignore
```

- **app/src/main/java/com/example/financepal/**: lógica principal, pantallas y controladores.
- **app/src/main/res/**: layouts, recursos gráficos y textos.

## 👥 Créditos y Autores

| Nombre              | GitHub                                          | Correo Institucional          |
| :------------------ | :---------------------------------------------- | :---------------------------- |
| **Cristofer Ordoñez** | [@cristoferOrdonez](https://github.com/cristoferOrdonez) | crordonezo@unal.edu.co        |
| **Farid Ardila** | [@faridardila](https://github.com/faridardila)  | deardilah@unal.edu.co         |
| **Diego Arévalo** | [@DiegoArevaloArias](https://github.com/DiegoArevaloArias) | darevaloa@unal.edu.co         |

## ⚖️ Licencia

Este proyecto se proporciona con fines **académicos y demostrativos**. Para cualquier otro uso, incluyendo fines comerciales, por favor contactar directamente a los autores para discutir los términos de la licencia.
