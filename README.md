# eMerchantPay Task

## Steps

### install and run postgres
database credentials used:
#### username: postgres
#### password: password

postgres
port:5432

### run spring application
#### .\mvnw spring-boot:run -f pom.xml

JWT authentication for web services:
###Admins
#### username: john@beatles.com
#### password: john
#### username: paul@beatles.com
#### password: paul

###Merchants
#### username: george@beatles.com
#### password: george
#### username: ringo@beatles.com
#### password: ringo

George is an inactive merchant for testing purposes

Merchants are initially imported from the file:
#### src\main\resources\rake.csv

A list of all rest apis can be imported to postman from the file:
#### src\main\resources\eMerchantPay task.postman_collection.json

### run web application
cd src\main\resources\emerchantpat-task-web-app
#### npm start

##Frontend 
visit: http://localhost:3000
visit: http://localhost:3000/merchants (use an admin user to see this as only admin can see all merchants)

