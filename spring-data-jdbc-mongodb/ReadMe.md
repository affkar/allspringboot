Initial Setup:
Start MongoDB server with default port.
mongod --dpath <dbpath>


Running the application: 
Just run Application.java. 

Tests: 
curl http://localhost:8080/demo/allCustomers
Should Result in []

curl http://localhost:8080/demo/addCustomer?firstName=Yogesh\&lastName=Fernando
Should result in a 200 http response code.

curl http://localhost:8080/demo/allCustomers
Should Result in [{"id":"5bf9c063a7ec644ac59e57ae","firstName":"Yogesh","lastName":"Fernando"}]