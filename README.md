# Technical Test using Spring Boot 2.6 and Java 8

## Prerequisites 👀
 
- Download JDK 8 https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html
- Configure JDK https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/index.html
- Download Maven https://maven.apache.org/download.cgi
- Configure Maven https://maven.apache.org/install.html

## How to run the app ▶️

1. Clone the Copper repository which contains the project.
2. Once you have configured the JDK and Maven, run the following commands to:
   1. `mvn install` Downloads all the dependencies found in pom.xml, compiles the project and runs the test.
   2. `mvn install -DskipTests` Same as previous command without running tests
   3. `mvn test` Runs only the tests
   4. `mvn spring-boot:start` Run Spring Boot app
   5. `mvn spring-boot:stop` Stop Spring Boot app

## How to test the app 🤓

### From Shell

Once the app is up and running you can perform the following actions from shell
1. `curl http://localhost:8080/copper/user-balance` to get the user's balance
   1. Expected structure response:
    ```
   [ 
      {"currency":"BTC","amount":132.0},
      {"currency":"ETH","amount":133.0},
      {"currency":"SOL","amount":70.0},
      {"currency":"USDC","amount":100.0}
   ] ```
2. `curl http://localhost:8080/copper/history` to get the history of user's deposits and withdrawals
   1. Expected structure response:
```[...
    {
        "type": "DEPOSIT",
        "transactionId": "0xdf240245f90e57ef6dfab0517c91a756eb6227efd10947d3d088430eb97b810d",
        "address": "0x2ccff48f4d934f244437dae18f6935481561244d",
        "date": "20-01-1970 12:13:27",
        "state": "completed",
        "currency": "ETH",
        "amount": 63,
        "fee": 0,
        "priority": 0
    },
    {
        "type": "WITHDRAWAL",
        "transactionId": "397ca24fb78e625d92fde843e0e9dd9696a6b0cf1bb73551850c878cfb1e0693",
        "address": "bcrt1q49mlrsaam7w0eljjjh38l6hcvcse4yr6xyyew0",
        "date": "22-01-1970 12:00:28",
        "state": "completed",
        "currency": "BTC",
        "amount": 0.00054,
        "fee": 0.0001,
        "priority": 1
    },
    ...]
```
3. `curl 'http://localhost:8080/copper/withdraw?address=bcrt1q49mlrsaam7w0eljjjh38l6hcvcse4yr6xyyew0&currency=BTC&amount=0.00054&priority=mid'` to make a withdrawal to an external address
   1. Expected structure response:
```
    { 
      "address" : "bcrt1q49mlrsaam7w0eljjjh38l6hcvcse4yr6xyyew0",
      "state" : "confirmed",
      "currency" : "BTC",
      "amount" : 0.00054,
      "fee" : 0.0002,
      "priority" : 1,
      "transactionDate" : "22-01-1970 13:58:28",
      "confirmationDate" : "22-01-1970 13:58:28"
    }
```
### From Postman

There is a Postman Collection (_Copper.postman_collection.json_) which contains the existing endpoints and examples of calls  for the different scenarios

Download Postman: https://www.postman.com/downloads/

## Assumptions 👍

1. To get the user's balance, I assumed we only want to know the currency and amount of money for each of them.
2. To get the history of user's deposits and withdrawal, I assumed both had to be returned in the same call. They are sorted by date.

## Improvements 🔨

1. Add more test cases to cover all functionalities.
2. Add validations and exception handling in all functionalities.
3. Implement idempotency when withdrawing to an external address by adding an idempotency-key and checking if the key already exist before proceeding.