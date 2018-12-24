Running the application: 
Just run Application.java. Nothing more required.


Tests: 
curl http://localhost:8080/demo/allCustomers
Should Result in [{"id":1,"firstName":"Vallimalar","lastName":"Karthick"},{"id":2,"firstName":"Karthick","lastName":"Sundaram"},{"id":3,"firstName":"Alagu","lastName":"Karthick"},{"id":4,"firstName":"Sivakami","lastName":"Karthick"}]

curl http://localhost:8080/demo/addCustomer?firstName=Yogesh\&lastName=Fernando
Should result in a 200 http response code.

curl http://localhost:8080/demo/allCustomers
Should Result in [{"id":1,"firstName":"Vallimalar","lastName":"Karthick"},{"id":2,"firstName":"Karthick","lastName":"Sundaram"},{"id":3,"firstName":"Alagu","lastName":"Karthick"},{"id":4,"firstName":"Sivakami","lastName":"Karthick"},{"id":5,"firstName":"Yogesh","lastName":"Fernando"}]