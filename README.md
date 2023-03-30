# eMerchantPay Task

## Steps

### install and run postgres
database credentials used:
#### username: postgres
#### password: password

postgres
port:5432

### run spring application
####.\mvnw spring-boot:run -f pom.xml

Basic authentication for web services:
#### username: username
#### password: password

Merchants are initially imported from the file:
####src\main\resources\rake.csv

A list of all rest apis can be imported to postman from the file:
####src\main\resources\eMerchantPay task.postman_collection.json

### run web application
cd src\main\resources\emerchantpat-task-web-app
####npm start

Not much done in frontend, visit: http://localhost:3000/merchants 

