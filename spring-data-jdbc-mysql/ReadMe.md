Initial Setup:
Start mysql server with default port. Create a database "test". Setup the connection details in src/main/resources/application.properties.
Set the _ddl-auto as create initially, then change to none. _

Running the application: 
Just run Application.java. 

Tests: 
curl http://localhost:8080/demo/allCustomers
Should Result in []

curl http://localhost:8080/demo/addCustomer?firstName=Yogesh\&lastName=Fernando
Should result in a 200 http response code.

curl http://localhost:8080/demo/allCustomers
Should Result in [{"id":1,"firstName":"Yogesh","lastName":"Fernando"}]