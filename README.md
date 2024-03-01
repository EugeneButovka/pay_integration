### Local run instructions

#### Setup
Before run add api key to **application.properties** file **payment-service.api-key** field

#### Run

Build the project
```bash
./gradlew build
```

Run the project
```bash
./gradlew bootRun
```

Test the project
```bash
./gradlew test
```

Generate openapi docs (will generate to build/openapi.json)
```bash
./gradlew clean generateOpenApiDocs
```

### API documentation
See 'Generate openapi docs' or swagger UI after running app locally at http://localhost:8080/swagger-ui.html

### Misc
project created with https://start.spring.io/#!type=gradle-project-kotlin&language=kotlin&platformVersion=3.2.3&packaging=jar&jvmVersion=17&groupId=com.eugenebutovka.test&artifactId=pay_integration&name=pay_integration&description=Demo%20project%20for%20Spring%20Boot&packageName=com.eugenebutovka.test.pay_integration&dependencies=devtools,configuration-processor,web,security,actuator,cloud-resilience4j
