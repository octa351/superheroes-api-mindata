----------------------------
# Pasos para levantar la aplicacion:

- Correr el siguiente comando de maven: `mvn clean package `
- Una vez generada la carpeta target con los jars correr: `docker build -t superhero-api .` para buildear la imagen
- A partir de la imagen generada correr el contenedor: `docker run -p 8080:8080 superheroes-api`
- Para poder entrar a la aplicacion la url es: http://localhost:8080/swagger-ui/index.html#/
------------------------------------------

# Aclaraciones
- Se levanto un server de oauth en Amazon con cognito para darle seguridad a las apis, el client id y secret para 
poder entrar al swagger se pasaran por mail
