# Prueba técnica Android junior para Doonamis: TVPop

[![Kotlin](https://img.shields.io/badge/Kotlin-%237F52FF.svg?logo=kotlin&logoColor=white)](https://kotlinlang.org/) [![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)](https://developer.android.com/)

Esta aplicación es una prueba técnica para Doonamis. *TVPop* es una aplicación nativa de Android
programada en Kotlin y basada en Jetpack Compose que muestra las series más populares obtenidas de
TMDB.

## Compilación

Antes de compilar el proyecto, es necesario especificar tu clave de la API de TMDB para poder
autenticar las peticiones. Para eso, deberás rellenar su valor en un fichero `env` a partir de la
plantilla proporcionada, [`env.template`](app/src/main/assets/env.template), siguiendo estos pasos:

1. Haz una copia de [`env.template`](app/src/main/assets/env.template) en un fichero llamado `env`, en la misma [carpeta](app/src/main/assets).
2. Especifica el valor de tu clave de la API de TMDB.