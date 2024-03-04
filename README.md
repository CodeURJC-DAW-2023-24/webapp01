![image](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/64bfe72f-86a9-47a3-8dce-0a016e0b8060)# webapp01
# Fase 0

## Nombre de la aplicación web
- **Tatademy**

## Tema de la aplicación web
- Venta de cursos académicos online. 

## Integrantes del grupo:
- Enrique Tentor Martín
  - e.tentor.2021@alumnos.urjc.es
  - @etentor
- Pedro Torrecilla
  - p.torrecilla.2021@alumnos.urjc.es
  - @p-torrecilla
- Javier Bringas García
  - j.bringas.2021@alumnos.urjc.es
  - @JavierBringas
- Jesús Mariscal Alonso
  - j.mariscal.2021@alumnos.ujrc.es
  - @masterjesus999
- Paula Ruiz Rubio
  - p.ruizr.2021@alumnos.urjc.es
  - @PaulaRuizRubio


## Herramienta de coordinación
Trello:
- [https://trello.com/invite/b/bXT0KXzy/ATTIe7a9323a8c26017d8cf67e800305ffc643CB3438/daw-proyecto](https://trello.com/invite/daw359/ATTI234911d5a220db201ad3876bbe912dbd0CC8CC0A)


## Descripción del proyecto: 
### Entidades
- Usuario, Curso, Material, Review.
  - Un usuario registrado está en un curso, que tiene material y un apartado de reviews.

### Usuarios
- Anónimo
- Registrado
- Administrador.

### Permisos de usuario
- Anónimo:
  - Buscar cursos y consultar su información

- Registrado:
  - Todos los permisos de Anónimo
  - Apuntarse o darse de baja de Cursos
  - Añadir o eliminar Reviews
  - Modificar su área personal

- Administrador: 
  - Todos los permisos de Registrado
  - Crear, modificar y eliminar cursos
  - Crear, modificar y eliminar usuarios
  - Crear, modificar y eliminar reviews
  - Crear, modificar y eliminar material

### Imágenes
- Avatares de los usuarios
- Miniatura de los cursos

### Gráficos
- Gráfico para visualizar las puntuaciones de las reviews por curso
- Gráfico con usuarios apuntados a un curso o en una categoría

### Tecnología complementaria
- Envío de correos
- Generador de PDFs con el diploma del curos una vez completado

### Algoritmos o consulta avanzada
- Recomendador de cursos: Dentro de la plataforma veo quién se parece a mi y miro a ver a que cursos se ha apuntado que yo no lo haya hecho. 

# Fase 1

## Mapa de navegación

![Mapa de navegación](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/911e5649-d90c-44a6-abd8-73b7ff4523d8)


No se ha incluido las posibles conexiones que genera el encabezado de la página para evitar una cantidad excesiva de flechas.

## Capturas de pantallas
### Inicio
![Inicio](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/44424f1c-deb7-4d6d-8a3e-34f5b32e44d2)


Pantalla a la que pueden acceder todos los usuarios. Desde aquí se puede acceder a la mayoría de las otras pantallas. También existen pequeñas variaciones para los administradores y para los usuarios registrados, incluyendo botones o funciones específicas para cada tipo de usuario.
### Login
![Login](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/4a27305d-29e2-40e3-82d6-ba9b9251039a)


Se trata de una página donde introducir el correo electrónico y la contraseña asociados a una cuenta para poder iniciar sesión, el formulario recogerá los datos y los enviará al servidor para su comprobación.
### Registro
![Registro](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/4e2189e4-205f-4c52-8af1-a39d0e756257)


El formulario de la pantalla recogerá el nombre, el correo electrónico y la contraseña del nuevo usuario, estos datos se enviarán al servidor para almacenar la información del nuevo usuario, además se comprobará en tiempo real la robustez de la contraseña.
### Recuperar contraseña
![Recuperar contraseña](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/b79f1701-8e29-4f99-9a25-273957bce23a)


Se recogerá mediante un formulario el correo electrónico del usuario que quiere recuperar su cuenta, y se le enviará un correo electrónico con los pasos a seguir.
### FAQ
![FAQ](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/f77b7050-f28e-4db2-9d72-660ac5a26b43)


Esta pantalla es accesible a todos los usuarios por igual, y se accede desde la pantalla de inicio. Aqué se muestran algunas preguntas frecuentes en formato desplegable, para optimizar el espacio. El usuario simplemente hace click en la pregunta y se desplegará la respuesta ahí mismo.
### Perfil
![Perfil](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/9eb3822a-f6df-4928-bce6-ab0d26c8cd6e)


Pantalla de perfil, se accede a esta pestaña clickando arriba a la derecha sobre la foto de perfil y pulsando posteriormente en "Mi perfil". En esta zona se pueden ver los datos personales y el avatar. Desde esta zona se puede cambiar el avatar y tener acceso a: Cambiar los datos que aparecen debajo del avatar, cambiar la contraseña, eliminar la cuenta y cerrar sesión
### Cambiar contraseña
![Cambiar contraseña](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/3aab91a8-6862-459c-91ba-00837f5d279f)


Para acceder a esta pestaña hay que pulsar en el avatar de arriba a la derecha y en la sección "Mi perfil". Después en el menú de lateral pulsar sobre "Cambiar contraseña". En este apartado se podrá cambiar la contraseña del usuario simplemente introduciendo la contraseña actual y repitiendo dos veces la nueva contraseña deseada. La nueva contraseña se regirá por estándares: mínimo 8 caracteres, un símbolo especial y al menos un numero.
### Eliminar cuenta
![Eliminar cuenta](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/2c3c7b65-d2c8-4217-a65f-7445db0f41fa)


Para acceder a esta pestaña hay que pulsar en el avatar de arriba a la derecha y en la sección "Mi perfil". Después en el menú de lateral pulsar sobre "Borrar perfil". En este apartado se podrá eliminar la cuenta del usuario actual (el que ha iniciado sesión). Una vez hecho esto, el usuario se elimina de la base de datos y de todos los cursos así como d ellas estadísticas en las que puntuaba. Sus reviews también se eliminan.
### Mis cursos
![Mis cursos](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/0633d81b-a2ce-4bbf-aff2-70f80e24ff39)


Pantalla de mis cursos, se accede a esta pestaña clickando arriba a la derecha sobre la foto de perfil y posteriormente sobre "Mis cursos". En esta zona se pueden ver todos los cursos a los que está el usuario inscrito, así como filtrar mediante una búsqueda o por categoría.
### Gráficos y estadísticas
![Gráficos y estadísticas](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/183199b9-58ae-4784-9947-9aef1ba37f46)


Pantalla para los usuarios registrados y administradores que se accede desde la pantalla de inicio. Aquí se muestran estadísticas relacionadas con los cursos y los usuarios.
### Buscador de cursos
![Buscador de cursos](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/0959c87e-8389-4f5a-bcc1-26db77df3215)


A la pantalla buscador de cursos, se accede pulsando sobre “Cursos”, situado en el menú superior. En esta pantalla, los usuarios utilizarán la barra de búsqueda para ingresar los criterios necesarios con el fin de encontrar el curso específico que desean. Al hacer clic en el botón de búsqueda, se mostrarán todos los cursos relacionados con los términos buscados.
### Vista de un curso
![Vista de un curso](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/ceba7d16-f81a-469e-9bf2-6e2792490634)


A la pantalla detalles/información del curso, se accede pulsando al curso deseado. Aquí se muestra el material del curso y las reseñas sobre el principalmente.
### Crear un curso
![Crear un curso](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/23746fc5-b22d-4b4d-ac1e-e28e3c53dad0)


Esta pantalla es solo accesible para los administradores, tanto desde la pantalla de inicio como desde la pantalla de gestión de cursos. Aquí se muestra una serie de formularios a completar para crear un curso nuevo al que luego se podrán unir los usuarios registrados. Cuenta con botones de navegación y de guardado.
### Vista de administrador
![Vista de administrador](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/84284730-c00c-4bde-9f99-54f92b0b6183)

Es una pantalla que funciona para que los administradores puedan gestionar a los usuarios y a los cursos de manera más eficiente.
### Panel de usuarios
![Panel de usuarios](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/561fc009-21c2-4fb2-92a2-c679e966d15c)


Esta pantalla solo es accesible para los administradores, a la que llegarán desde la pantalla de "Vista de administrador". Aquí, los administradores pueden gestionar a los usuarios de la aplicación, así como crear o eliminar perfiles. También utilizando el panel de la izquierda, pueden controlar diversas estadísticas y otros valores.
### Vista de administrador de un curso
![Vista de administrador de un curso](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/4e9bb803-aaf4-4fe1-89e4-4203304998b0)


A la pantalla detalles/información del curso desde el punto de vista del administrador, se accede pulsando al curso deseado. Aquí se muestra la misma información que en la pantalla detalles/información del curso, pero con la adición de dos botones: "Editar curso" y "Eliminar curso". El botón "Editar curso" permite al administrador modificar toda la información relacionada con el curso, mientras que el botón "Eliminar curso" proporciona la opción de eliminar completamente el curso.

# Fase 2

## Mapa de navegación

El mapa de navegación no se ha alterado respecto a la anterior fase.

## Capturas de pantallas
### Inicio
![Inicio](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/57a1609b-1e54-45ad-b5ce-981a74e4ffa4)


Pantalla a la que pueden acceder todos los usuarios. Desde aquí se puede acceder a la mayoría de las otras pantallas. También existen pequeñas variaciones para los administradores y para los usuarios registrados, incluyendo botones o funciones específicas para cada tipo de usuario.
### Login
![Login](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/4a27305d-29e2-40e3-82d6-ba9b9251039a)


Se trata de una página donde introducir el correo electrónico y la contraseña asociados a una cuenta para poder iniciar sesión, el formulario recogerá los datos y los enviará al servidor para su comprobación.
### Registro
![Registro](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/df5827a1-2b8e-46a2-bec0-74ef71337b46)


El formulario de la pantalla recogerá el nombre, los apellidos, el correo electrónico y la contraseña del nuevo usuario, estos datos se enviarán al servidor para almacenar la información del nuevo usuario, además se comprobará en tiempo real la robustez de la contraseña.
### Recuperar contraseña
![Recuperar contraseña](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/b79f1701-8e29-4f99-9a25-273957bce23a)


Se recogerá mediante un formulario el correo electrónico del usuario que quiere recuperar su cuenta, y se le enviará un correo electrónico con los pasos a seguir.
### FAQ
![FAQ](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/4a0e4f5b-f15d-4c7b-9ee1-035b26ae0ee7)


Esta pantalla es accesible a todos los usuarios por igual, y se accede desde la pantalla de inicio. Aqué se muestran algunas preguntas frecuentes en formato desplegable, para optimizar el espacio. El usuario simplemente hace click en la pregunta y se desplegará la respuesta ahí mismo.
### Perfil
![Perfil](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/86d6c1f1-ea2a-4572-a23b-5efc5c029c2c)



Pantalla de perfil, se accede a esta pestaña clickando arriba a la derecha sobre la foto de perfil y pulsando posteriormente en "Mi perfil". En esta zona se pueden ver los datos personales y el avatar. Desde esta zona se puede cambiar el avatar y tener acceso a: Cambiar los datos que aparecen debajo del avatar, cambiar la contraseña, eliminar la cuenta y cerrar sesión
### Cambiar contraseña
![Cambiar contraseña](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/3aab91a8-6862-459c-91ba-00837f5d279f)


Para acceder a esta pestaña hay que pulsar en el avatar de arriba a la derecha y en la sección "Mi perfil". Después en el menú de lateral pulsar sobre "Cambiar contraseña". En este apartado se podrá cambiar la contraseña del usuario simplemente introduciendo la contraseña actual y repitiendo dos veces la nueva contraseña deseada. La nueva contraseña se regirá por estándares: mínimo 8 caracteres, un símbolo especial y al menos un numero.
### Eliminar cuenta
![Eliminar cuenta](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/2c3c7b65-d2c8-4217-a65f-7445db0f41fa)


Para acceder a esta pestaña hay que pulsar en el avatar de arriba a la derecha y en la sección "Mi perfil". Después en el menú de lateral pulsar sobre "Borrar perfil". En este apartado se podrá eliminar la cuenta del usuario actual (el que ha iniciado sesión). Una vez hecho esto, el usuario se elimina de la base de datos y de todos los cursos así como d ellas estadísticas en las que puntuaba. Sus reviews también se eliminan.
### Mis cursos
![Mis cursos](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/0633d81b-a2ce-4bbf-aff2-70f80e24ff39)


Pantalla de mis cursos, se accede a esta pestaña clickando arriba a la derecha sobre la foto de perfil y posteriormente sobre "Mis cursos". En esta zona se pueden ver todos los cursos a los que está el usuario inscrito, así como filtrar mediante una búsqueda o por categoría.
### Gráficos y estadísticas
![Gráficos y estadísticas](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/183199b9-58ae-4784-9947-9aef1ba37f46)


Pantalla para los usuarios registrados y administradores que se accede desde la pantalla de inicio. Aquí se muestran estadísticas relacionadas con los cursos y los usuarios.
### Buscador de cursos
![Buscador de cursos](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/0959c87e-8389-4f5a-bcc1-26db77df3215)


A la pantalla buscador de cursos, se accede pulsando sobre “Cursos”, situado en el menú superior. En esta pantalla, los usuarios utilizarán la barra de búsqueda para ingresar los criterios necesarios con el fin de encontrar el curso específico que desean. Al hacer clic en el botón de búsqueda, se mostrarán todos los cursos relacionados con los términos buscados.
### Vista de un curso
![Vista de un curso](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/ceba7d16-f81a-469e-9bf2-6e2792490634)


A la pantalla detalles/información del curso, se accede pulsando al curso deseado. Aquí se muestra el material del curso y las reseñas sobre el principalmente.
### Crear un curso
![Crear un curso](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/23746fc5-b22d-4b4d-ac1e-e28e3c53dad0)


Esta pantalla es solo accesible para los administradores, tanto desde la pantalla de inicio como desde la pantalla de gestión de cursos. Aquí se muestra una serie de formularios a completar para crear un curso nuevo al que luego se podrán unir los usuarios registrados. Cuenta con botones de navegación y de guardado.
### Vista de administrador
![Vista de administrador](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/84284730-c00c-4bde-9f99-54f92b0b6183)

Es una pantalla que funciona para que los administradores puedan gestionar a los usuarios y a los cursos de manera más eficiente.
### Panel de usuarios
![Panel de usuarios](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/67952cb7-f21e-4e5e-8dcc-43b9b7172d7e)



Esta pantalla solo es accesible para los administradores, a la que llegarán desde la pantalla de "Vista de administrador". Aquí, los administradores pueden gestionar a los usuarios de la aplicación, así como crear o eliminar perfiles. También utilizando el panel de la izquierda, pueden controlar diversas estadísticas y otros valores.
### Vista de administrador de un curso
![Vista de administrador de un curso](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/d49a85e8-ee30-4f2f-923f-88094375d0d4)



A la pantalla detalles/información del curso desde el punto de vista del administrador, se accede pulsando al curso deseado. Aquí se muestra la misma información que en la pantalla detalles/información del curso, pero con la adición de dos botones: "Editar curso" y "Eliminar curso". El botón "Editar curso" permite al administrador modificar toda la información relacionada con el curso, mientras que el botón "Eliminar curso" proporciona la opción de eliminar completamente el curso.

## Instrucciones de ejecución: 
Se indicará qué pasos deben seguirse para poder descargar el código del repositorio, construir y ejecutar la aplicación. También se debe especificar cuáles son los requisitos (versión de Java, versión de MySQL, maven, etc.). Las instrucciones se especificarán preferentemente como comandos a ejecutar por la línea de comandos. En caso de que no sea posible, se indicará cómo instalar/configurar aplicaciones de forma interactiva.
Instrucciones para la instalación y ejecución de la aplicación:
Asegúrate de tener instalados previamente los siguientes requisitos especificados en el archivo pom.xml:
-
Java 17
-
MySQL 8.0.23
-
Maven 4.0.0
-
Spring Boot 3.2.2
1.
Descargar el código del repositorio:
Utiliza el siguiente comando para clonar el repositorio desde GitHub:
git clone
https://github.com/CodeURJC DAW 2023 24/webapp01.git
2.
Construir la aplicación: Navega al directorio del proyecto clonado usando el comando cd webapp01 y luego ejecuta el siguiente comando Maven para construir la aplicación:
mvn clean install
3.
Configurar MySQL: Asegúrate de que MySQL esté en ejecución y de que exista la base de datos que tu aplicación necesita. Puedes hacer esto interactivamente a través de un cliente MySQL como MySQL Workbench. En tu servidor local de MySQL, crea una base de datos con el nombre que requiera tu aplicación.
4.
Configurar la aplicación: Una vez descargada la aplicación, en un entorno de desarrollo con la extensión de Spring preparada de antemano, abre el archivo application.properties y configura la contraseña y el usuario que tengas en tu base de datos de MySQL.
5.
Ejecutar la aplicación: Una vez construida la aplicación, puedes ejecutarla con java -jar target/Tatademy-local-Fase 2 DAW.jar
6.
Acceder a la aplicación: Sin parar la ejecución del programa, introduce la siguiente URL en un navegador: https://localhost:8443/. Una vez que se cargue la pantalla de la tienda, la aplicación estará lista para usarse.
Siguiendo estos pasos, podrás descargar, construir y ejecutar la aplicación Spring Boot utilizando MySQL como base de datos. Asegúrate de cumplir con los requisitos especificados y realizar la configuración necesaria antes de ejecutar la aplicación.

## Diagrama de las entidades de la base de datos:
![Diagrama de las entidades de la base de datos](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/d0d2a6b2-eb47-488b-9253-b83008c800b6)

## Diagrama de clases y templates:
![Diagrama-clases](https://github.com/CodeURJC-DAW-2023-24/webapp01/assets/116177156/9e3d1135-ffb7-4ee7-8569-a3c4854efaff)

## Participación de los miembros:
# Pedro Torrecilla:

  Tareas realizadas:
  Realicé las funcionalidades para que se vea la información de perfil y que esta misma se pueda editar
  En el perfil del usuario, también habilité los hipervínculos y la posibilidad de cerrar sesión
  También estaba encargado de la página de detalles de un curso
  Allí también incluí la posibilidad de inscribirse a un curso
  Diseñé el diagrama de clases
  Hice una primera instancia de la habilitación de https y el fichero keystore.jks

  Listado 5 commits más significativos de la fase:
  - [Showing profile information](https://github.com/CodeURJC-DAW-2023-24/webapp01/commit/e360ee6b1e2ff9d8d98b4d68b145667ff3d27d73)
  - [Update to the image name when uploaded](https://github.com/CodeURJC-DAW-2023-24/webapp01/commit/49b8e693804990d56e295ab8178d667ffdf52bae)
  - [Course details and inscription](https://github.com/CodeURJC-DAW-2023-24/webapp01/commit/e3aee243cd779605ce3e26f4064f2d0ec835fe39)
  - [Log out and code cleaning](https://github.com/CodeURJC-DAW-2023-24/webapp01/commit/c5d24146161a9803fe11a2b9c8c79b23b1e3bbf7)
  - [Setting up https](https://github.com/CodeURJC-DAW-2023-24/webapp01/commit/fd4599a3a767cb1719e4df189c23e5e7d6271e8e)

  Listado 5 ficheros en los que más haya participado:
  - CourseController.java
  - UserLoggedController.java
  - setting-edit-profile.html
  - course-details.html
  - CourseRepository.java

# Enrique Tentor Martín:

  Tareas realizadas:
  Me he encargado de la parte de estructuración del proyecto, uso de plantillas e inyecciones mustache. Plantillas genericas de header y footer. Desde registrarse como usuario y login hasta la parte de     perfil de usuario y todas sus funcionalidades. Recuperación de contraseña, generación de una nueva y enviar correos. La parte de seguridad de la web, https y csrf. Configuración del                 
  application.properties.

  Listado 5 commits más significativos de la fase:
  - [Structure, templates and resources](https://github.com/CodeURJC-DAW-2023-24/webapp01/commit/9e7c2e1321cea83cbe6a65a90a936b161154dee3)
  - [User registration, refactoring and bug fixes](https://github.com/CodeURJC-DAW-2023-24/webapp01/commit/75bb8788d1fd3f9200dd4e2e098c72424e851d82)
  - [Spring Security and User login](https://github.com/CodeURJC-DAW-2023-24/webapp01/commit/8c2bb34710c215145c945b782fce0e04964d1b69)
  - [DatabaseInitializer-DataModelFixes-TemplatesStandardization-WebSecurity](https://github.com/CodeURJC-DAW-2023-24/webapp01/commit/b00549dee0d63d47c9b3456242253920ae0c1f71)
  - [ForgotPassword & SendEmails & PasswordGenerator & Templates bug fixes](https://github.com/CodeURJC-DAW-2023-24/webapp01/commit/35b6428f54dc4bbfad533274a2d2266a67c53100)

  Listado 5 ficheros en los que más haya participado:
  - UserLoggedController
  - UserAnonymousController
  - WebSecurityConfig
  - HomeController
  - DatabaseInitializerService

# Jesús Mariscal Alonso:

  Tareas realizadas:
  Crear base de datos. Crear, actualizar (editar) y eliminar cursos. Editar, eliminar mostrar panel de usuarios siendo administrador implementado con AJAX. Creación del generador de pdfs (certificados) y su implementación dinámica para los cursos. Descarga de archivos de los cursos.

  Listado 5 commits más significativos de la fase:
  - [Initialize DB](https://github.com/CodeURJC-DAW-2023-24/webapp01/commit/55690ad5bad38896c4af6821c0799dedec710744)
  - [Course creation](https://github.com/CodeURJC-DAW-2023-24/webapp01/commit/53a7133a62fdd7a7ccadf9ec42e04107e0ef65ca)
  - [Admin header & its fonctionalities]([https://github.com/CodeURJC-DAW-2023-24/webapp01/commit/8c2bb34710c215145c945b782fce0e04964d1b69](https://github.com/CodeURJC-DAW-2023-24/webapp01/commit/cbc5ea6a03c187a4b37fb8d5ed106703985fa4bb))
  - [Algorithm creation](https://github.com/CodeURJC-DAW-2023-24/webapp01/commit/ce46508eaac3d6fa61bcaf01dc269d1c570c3dc3)
  - [PDF generator created](https://github.com/CodeURJC-DAW-2023-24/webapp01/commit/284b8343664ae88b562ad33f225cc50893a5bc0d)

  Listado 5 ficheros en los que más haya participado:
  - AdminUserManager.java
  - CourseController.java
  - add-course.html
  - instructor-edit-profile.html
  - course-details.html

# Paula Ruiz Rubio:

  Tareas realizadas:
  - En lo relativo a un curso:
    En primer lugar, se implementó la capacidad de mostrar información detallada sobre cada curso disponible en la plataforma. Esto incluyó aspectos esenciales como el nombre del curso, su descripción,       la puntuación media proporcionada por los usuarios, el número total de reseñas realizadas, la cantidad de estudiantes matriculados, así como también el número de materiales asociados al curso y su    
    categoría.
    Para mejorar la interacción del usuario con la plataforma, se habilitó la visualización de todas las reseñas disponibles para cada curso y el formulario para añadir una reseña al curso. Finalmente,       se desarrolló una funcionalidad que permitía a los usuarios inscribirse en los cursos. Para realizar todo ello, se adaptó la plantilla de cursos a los cambios realizados
  - En lo referente a las estadísticas:
    Se implementaron dos funciones, una para recopilar los datos de la creación de un curso por meses y otra para las reviews. Se adaptó el archivo "chart-data.js" para procesar estos datos y permitir la     creación de gráficos.  Además, se ajustó la plantilla de los gráficos para reflejar estos cambios.

  Listado 5 commits más significativos de la fase:
  - Course-details In Main (reviews & Materials)
  - Statistics (without changing control panel)
  - Statistics page (finished)
  - Correction in the calculation of reviews
  - Show and interact with course info

  Listado 5 ficheros en los que más haya participado:
  - CourseController.java
  - Chart-data.js
  - UserLoggedController.java
  - Instructor-dashboard.html
  - Course-details.html

# Javier Bringas García

  Tareas realizadas:
  Me he encargado de la pantalla de incio, el manejo de cursos al mostrarlos y buscarlos en la base de datos, tanto para usuarios comunes como para administradores, así como dar apoyo en los listados de    usuarios.

  Listado 5 commits más significativos de la fase:
  - [Structure, templates and resources](https://github.com/CodeURJC-DAW-2023-24/webapp01/commit/9e7c2e1321cea83cbe6a65a90a936b161154dee3)
  - [User registration, refactoring and bug fixes](https://github.com/CodeURJC-DAW-2023-24/webapp01/commit/75bb8788d1fd3f9200dd4e2e098c72424e851d82)
  - [Spring Security and User login](https://github.com/CodeURJC-DAW-2023-24/webapp01/commit/8c2bb34710c215145c945b782fce0e04964d1b69)
  - [DatabaseInitializer-DataModelFixes-TemplatesStandardization-WebSecurity](https://github.com/CodeURJC-DAW-2023-24/webapp01/commit/b00549dee0d63d47c9b3456242253920ae0c1f71)
  - [ForgotPassword & SendEmails & PasswordGenerator & Templates bug fixes](https://github.com/CodeURJC-DAW-2023-24/webapp01/commit/35b6428f54dc4bbfad533274a2d2266a67c53100)

  Listado 5 ficheros en los que más haya participado:
  - UserLoggedController
  - UserAnonymousController
  - WebSecurityConfig
  - HomeController
  - DatabaseInitializerService
