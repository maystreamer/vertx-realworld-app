# vertx-realworld-app
Application trying to demonstrate authentication, session management, public and protected APIs, vertx messaging capabilities and data handling using MongoDB, Vertx Reactivex and EventBus.

## Getting Started

Git clone the project on your local machine and import it to your favorite ide.

### Prerequisites

For runnning this, you will need
- Java 1.8
- Gradle support - In Eclipse editor, goto help -> eclipse marketplace -> search for buildship (buildship gradle integration) and install it.

## Brief

This application allows user to signup, send welcome email, activates user, authenticates user, manages the session for the user and allows authenticated user to create another user. It shows the usage of Public and Private APIs.
- **AppLauncher**        -> The starting point of the application. It is used to set the app configuration.
- **MainVerticle**       -> Main verticle deploys all the other verticles used in the program.
- **Handlers**           -> Handlers are basically the controllers which receives the input, process the input and returns the Json response back to the user.

## Running the app

For running the app, (IDE used here is Eclipse)
- Open **appConfig.json** file and set the "http_server_port" as per your choice. Also, update the "mailConfig" settings as per your mail provider.
- Once, changes are done in **appConfig.json**, right click on the project("vertx-realworld-app"), <br />select "Run As" -> "Run Configurations". Set:
  * **Main**: io.vertx.realworld.AppLauncher
  * **Program arguments**: <br />run io.vertx.realworld.verticle.MainVerticle -conf ../vertx-realworld-app/src/main/resources/appConfig.json
  * **VM arguments**: -Dlogback.configurationFile=file:../vertx-realworld-app/src/main/resources/logback.xml <br /><br /> After setting the variables, click "Run".<br />
- If app starts successfully, goto **http://localhost:8080/runner/api/ping**. Status json {"code":200,"message":"success","hasError":false,"data":{"status":"OK"}} will be served as response.
- For signing-up the user, do <br />
**Type:** *POST http://localhost:8080/runner/api/user/signup* <br />
**Headers:** *Content-Type: application/json* <br />
**Data to send:** *json data below -> I have used mailinator here to receive the welcome email*
```
{
  "firstName": "Grey",
  "lastName": "Seal",
  "email": "mail.greyseal@mailinator.com",
  "password": "password"
}
```
Successful Response would be: A temporary link will be send back. However, this temporary link will also be part of the welcome email. You have to click "Activate Account" button<br />
```
{
  "code": 200,
  "message": "success",
  "hasError": false,
  "data": {
    "link": "http://localhost:8080/runner/api/link/c9f2f133a5b445368b23c4ff8cf71bef"
  }
}
```
Alternatively, you can also Activate the account using API
- For activating the user, do <br />
**Type:** *GET http://http://localhost:8080/runner/api/link/{{unique_link_here_from_signup_response}}* <br />
Successful Response would be: <br />
```
{
  "code": 200,
  "message": "success",
  "hasError": false,
  "data": {
    "message": "Account has been activated successfully."
  }
}
```
- After signing-up, time to authenticate the user, do <br />
**Type:** *POST http://localhost:8080/runner/api/session/authenticate* <br />
**Headers:** *Content-Type: application/json* <br />
**Data to send:** *json data below ->*
```
{
  "email": "mail.greyseal@mailinator.com",
  "password": "password"
}
```
Successful Response would be: <br />
```
{
  "code": 200,
  "message": "success",
  "hasError": false,
  "data": {
    "email": "mail.greyseal@mailinator.com",
    "firstName": "Grey",
    "lastName": "Seal"
  }
}
```
**Response Headers on successful authentication:** <br />
Authorization   BEARER c1d887063c3e492b9951b0479fadddda<br />
CORRELATION-ID  5a619f45a2f14615a7ba4346<br />

- For creating new users after authentication, do <br />
**Type:** *POST http://localhost:8080/runner/api/user* <br />
**Headers:** *Content-Type: application/json*;  *Authorization: BEARER c1d887063c3e492b9951b0479fadddda* <br />
**Data to send:** *json  data below ->*
```
{
  "firstName": "Vertx",
  "lastName": "User",
  "email": "vertx.user@mailinator.com",
  "password": "password"
}
```
Successful Response would be: <br />
```
{
  "code": 200,
  "message": "success",
  "hasError": false,
  "data": {
    "email": "smanuael@gmail.com",
    "firstName": "Sam",
    "lastName": "Manuael"
  }
}
```
## Note
Execute the below scripts to the mongo collections <br />
db.app_users.createIndex( { "email": 1 }, { unique: true } ) <br />
db.sessions.createIndex( { "token": 1 }, { expireAfterSeconds: 86400 } ) <br />
db.temporary_links.createIndex( { "link": 1 }, { expireAfterSeconds: 86400 } )<br />

## Built With

* [Vertx](http://vertx.io/) - The web framework used
* [Gradle](https://gradle.org/) - Dependency Management
