## Proxy Pets Spring API

## Requirements
### For local deployment:
* Java 1.8
* Spring Boot 2.5.1
* Maven 3.8.1

## Deployment

### Local deployment
1. Clone git repository:
    ```
    https://github.com/Sloydeath/Pets-Spring.git
    ```
2. Install PostgreSQL and create database pets_db.
3. Connect to database from Intellij.
4. Change spring.datasource.username and spring.datasource.password in file:
    ```
    src/main/resources/application-dev.properties
    ```
   P.S.: If you have changed post of db (by default 5432) 
   or name of database (pets_db), you should update 
   spring.datasource.url in the same file from point №3:
   ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/pets_db
   ```
5. Build project with command:
    ```
    mvn -Pdev clean spring-boot:run
    ```
6. If you want to test app, you should install postman and import files:
    ```
    postman/Pets Local.postman_collection.json
    postman/URLS_FOR_PETS_API.postman_environment.json
    ```