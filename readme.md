# Api partner for B2Boost 🚀

Doc Writer [art-dambrine.ovh](https://art-dambrine.ovh) [@art-dambrine]



### Framework and build tools

For this project I chose to use Spring Boot alongside Gradle.



### Api documentation

In the following document you will find a simple documentation of the API. Most of the following sections have been generated with  [Spring Rest Docs](https://spring.io/projects/spring-restdocs).

You will find the documentation in the **docs.html** at the root of this project, it's also available here 👉 [API DOCUMENTATION](https://art-dambrine.ovh/asciidoc/docs/api-partner-for-b2boost.html)



### Useful commands

*Assuming you are using a bash cli*

### Run the app in dev env

```
./gradlew bootRun
```

### Run the test suite with gradle

```
./gradlew test
```

### Prepare the jar archive with gradle for deployment

```
./gradlew clean bootJar
```
> Path of the output is `build/libs/api-spring-gradle-b2boost-0.0.1-SNAPSHOT.jar`

> I already have a built jar that you can find in the `dist` directory 

### Run the dist jar
```
java -jar dist/api-spring-gradle-b2boost-0.0.1-SNAPSHOT.jar
```