> [!IMPORTANT]
> 
>⚠️ Commit o numerze `5c86130` zawiera implementację zadania z wykorzystaniem biblioteki Lombok, która ułatwia pisanie getterów, setterów oraz wzorca projektowego Builder, dzięki czemu kod jest znacznie bardziej czytelny. Niestety, z powodu problemu z Lombokiem, budowanie aplikacji za pomocą polecenia `mvn clean package` nie działało, choć uruchamianie klasy SpringBootApplication (`InterTaskApplication`) przebiegało poprawnie.
> 
>W związku z tym, w commicie o numerze `418eb14` przygotowałem wersję bez użycia Lomboka, z ręcznie napisanymi getterami, setterami oraz klasami builder. Ta wersja pozwala na poprawne budowanie aplikacji za pomocą mvn clean package i java -jar target/InterTask-0.0.1-SNAPSHOT.jar

# Charity Collection Manager

## Technologies

- Java 21
- Spring Boot 3.5.0
- H2 In-Memory Database
- Maven

## Running the Application

### Run 
1. Clone the repository from GitHub:

`git clone https://github.com/gszczure/InterTask.git`

2. Open the project in your IDE (e.g., IntelliJ IDEA).
3. Run the Spring Boot application by executing the main class :

`InterTaskApplication`

OR

### Build and Run
```bash
mvn clean package
```
```bash
java -jar target/InterTask-0.0.1-SNAPSHOT.jar
```

## H2 Console

The H2 console is enabled and can be accessed at:
`http://localhost:8080/h2-console`

Connection details:
- JDBC URL: `jdbc:h2:mem:charitydb`
- Username: `greg`
- Password: `123456`

## REST API Endpoints

- `POST /api/events` - Create a new event


- `POST /api/boxes/register` - Register a new box


- `GET /api/boxes` - List all collection boxes (with info if assigned and if empty, WITHOUT details)


- `DELETE /api/boxes/delete/{id}` - Delete a box


- `PUT /api/boxes/{id}/assign/{eventId}` - Assign a collection box to an event


- `POST /api/boxes/{id}/money` - Add money to a box


- `POST api/boxes/{id}/withdraw` - Empty a box (transfer funds to the event’s account)


- `GET /api/financial-report` - Financial report of all events

## Testing Endpoints

You can test the API endpoints using Postman by importing the collection and environment from the following link:

[Postman Workspace with API Requests](https://web.postman.co/1cc9c392-de77-44ac-83ad-8f94b7796ef3)

This workspace contains all requests needed for testing the application.

Or you can test endpoints using curl commands in bash

### 1. Create a new event
```bash
curl -X POST http://localhost:8080/api/events \
-H "Content-Type: application/json" \
-d '{"name":"Charity One","currency":"EUR"}'
```
### 2. Register a new box
```bash
curl -X POST http://localhost:8080/api/boxes/register
```
### 3. List all collection boxes (with info if assigned and if empty, WITHOUT details)
```bash
curl -X GET http://localhost:8080/api/boxes
```
### 4. Delete a box
```bash
curl -X DELETE http://localhost:8080/api/boxes/delete/1
```
### 5. Assign a collection box to an event
```bash
curl -X PUT http://localhost:8080/api/boxes/1/assign/1
```
### 6. Add money to a box
```bash
curl -X POST http://localhost:8080/api/boxes/1/money \
-H "Content-Type: application/json" \
-d '{"currency":"USD","amount":15.78}'
```
### 7. Empty a box (transfer funds to the event’s account)
```bash
curl -X POST http://localhost:8080/api/boxes/1/withdraw
```
### 8. Financial report of all events
```bash
curl -X GET http://localhost:8080/api/financial-report
```

## Tests

The task includes around 15 unit tests covering the key use cases of the application. These tests verify, among others:

- Creating new event and registering a box.


- Retrieving objects by ID and handling cases when the object does not exist (throwing exceptions).


- Assigning a box to an event and validating the box’s state (whether it is empty).


- Adding money to the box in various scenarios, such as when the box is empty, already contains other currencies, or when the box is not assigned to an event (checking for exceptions).


- Withdrawing money from the box and updating the event balance, including handling error cases (box not assigned).


- Generating a financial report with multiple events or no events at all (verifying report correctness).
