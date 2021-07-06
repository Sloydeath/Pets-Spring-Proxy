## Proxy Pets Spring API

## Requirements
### For local deployment:
* Java 1.8
* Spring Boot 2.5.1
* Maven 3.8.1

## Deployment

### Cloud Foundry deployment
1. Clone git repository:
    ```
    https://github.com/Sloydeath/Pets-Spring-Proxy.git
    ```

2. Build project with command:
    ```
    mvn clean install
    ```

3. Install cf CLI on your computer.
4. Sign up on the site and create trial account:
   ```
   https://account.hanatrial.ondemand.com/
   ```
5. To log in to the cf CLI:
   * Run in cmd:
     ```
     cf login -a API-URL -u USERNAME -p PASSWORD -o ORG -s SPACE
     ```
     Where:
   * API-URL is your API endpoint, the URL of the Cloud Controller in your Cloud Foundry instance
   * USERNAME is your username.
   * PASSWORD is your password. Cloud Foundry discourages using the -p option, as it may record your password in your shell history.
   * ORG is the org where you want to deploy your apps.
   * SPACE is the space in the org where you want to deploy your apps.
   * More information you can find in CF documentation:
     ```
     https://docs.cloudfoundry.org/cf-cli/getting-started.html
     ```

6. Open cmd in the root folder of project and run:
   ```
   cf push
   ```

7. If you want to debug application, you should run this commands in CMD:
   ```
   cf login
   cf allow-space-ssh dev
   cf enable-ssh proxy-pets
   cf restage proxy-pets
   cf ssh -N -T -L 5041:localhost:8000 proxy-pets
   ```

8. If you want to test app, you should install postman and import files:
    ```
    postman/Pets_CF.postman_collection.json
    postman/URLS_FOR_PETS_API.postman_environment.json
    ```