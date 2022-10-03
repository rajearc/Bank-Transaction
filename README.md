# Bank-Transaction
Api for banking operation (Assignment)

## Tech-Stack
1. Java
2. Spring Boot (Rest API, JPA), Maven
3. H2 (for in-memory DB)
4. Mockito
5. Slf4j for logging
6. Swagger2 for API documentation
9. Docker

## End-Points
1. GET - /v1/accounts/{accountId}/details
2. POST - /v1/transaction 
 -  Body : {
    "accountFromId":"1000321",
    "accountToId":"2000121",
    "amount":"100"
   }

## Swagger Page :
http://localhost:8080/swagger-ui.html
