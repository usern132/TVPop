# Prueba técnica Android junior para Doonamis: TVPop

[![Kotlin](https://img.shields.io/badge/Kotlin-%237F52FF.svg?logo=kotlin&logoColor=white)](https://kotlinlang.org/) [![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)](https://developer.android.com/)

Esta aplicación es una prueba técnica para Doonamis. *TVPop* es una aplicación nativa de Android
programada en Kotlin y basada en Jetpack Compose que muestra las series más populares obtenidas de
TMDB.

## Compilación

Antes de compilar el proyecto, es necesario especificar tu clave de la API de TMDB para poder
autenticar las peticiones. Rellena su valor en el fichero `local.properties` (generado por Android
Studio en la raíz del proyecto) a partir de la plantilla proporcionada, [
`local.properties.template`](local.properties.template). Copia el contenido de la plantilla al final
de `local.properties` y rellena el valor de la clave.
