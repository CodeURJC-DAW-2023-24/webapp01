# webapp01
#Fase 0

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

#Fase 1

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
