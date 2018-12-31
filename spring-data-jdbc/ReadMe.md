##An example to demonstrate **Spring-data-jdbc** with the aid of spring boot.

###Pre-requisites
1. Maven or Gradle
2. Java 8 or above
3. Connection to the internet for downloading dependencies.

###Running
1. Import with Gradle or Maven into IntelliJIdea or with Maven into eclipse.
2. Running the application:   
Just right click on Application.java and run. 
3. On your browser, go to http://localhost:8080/h2-console with the below credentials 
| Key  | Value  |  
|---|---|  
| JDBC URL  | jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE  |  
| Username  | sa  |  
| Password  | <blank password>  |  
and click on connect. You should see the Customer/CUSTOMER table on the left. Executing 'SELECT * FROM CUSTOMER' should return 5 records. 


###Manual Tests
1. curl http://localhost:8080/demo/allCustomers  #COMMAND  
    should Result in  
    [{"id":1,"firstName":"Brad","lastName":"Pitt"},{"id":2,"firstName":"Tom","lastName":"Hanks"},{"id":3,"firstName":"Harrison","lastName":"Ford"},{"id":4,"firstName":"Russell","lastName":"Crowe"},{"id":5,"firstName":"Jude","lastName":"Law"}]

2. curl -X "DELETE" http://localhost:8080/demo/deleteCustomer #COMMAND    
   should result in a HTTP 200.

3. curl http://localhost:8080/demo/allCustomers #COMMAND  
   should Result in  
   []

4. curl http://localhost:8080/demo/reload #COMMAND    
   should result in a HTTP 200.

5. curl http://localhost:8080/demo/allCustomers  #COMMAND  
   should Result in  
   [{"id":1,"firstName":"Brad","lastName":"Pitt"},{"id":2,"firstName":"Tom","lastName":"Hanks"},{"id":3,"firstName":"Harrison","lastName":"Ford"},{"id":4,"firstName":"Russell","lastName":"Crowe"},{"id":5,"firstName":"Jude","lastName":"Law"}]

5. curl -d "firstName=Sandra&lastName=Bullock" http://localhost:8080/demo/addCustomer  #COMMAND      

   _same as_ 
   
   curl -X "POST" -d "firstName=Sandra&lastName=Bullock" http://localhost:8080/demo/addCustomer  #COMMAND  
   should result in a 200 http response code.  
   curl -d stands for a POST.   

6. curl http://localhost:8080/demo/allCustomers  #COMMAND  
   should Result in  
   [{"id":1,"firstName":"Brad","lastName":"Pitt"},{"id":2,"firstName":"Tom","lastName":"Hanks"},{"id":3,"firstName":"Harrison","lastName":"Ford"},{"id":4,"firstName":"Russell","lastName":"Crowe"},{"id":5,"firstName":"Jude","lastName":"Law"},{"id":6,"firstName":"Sandra","lastName":"Bullock"}]

5. curl -d "firstName=Sandra&lastName=Bullock" http://localhost:8080/demo/addCustomer  #COMMAND      
   
   _same as_ 
   
   curl -X "POST" -d "firstName=Sandra&lastName=Bullock" http://localhost:8080/demo/addCustomer  #COMMAND  
   should result in a 200 http response code.  
   curl -d stands for a POST.   
   Let us execute both 2 times and we should see two original and two duplicate records.  

   [{"id":7,"firstName":"Sandra","lastName":"Bullock"},{"id":8,"firstName":"Sandra","lastName":"Bullock"},{"id":9,"firstName":"Sandra","lastName":"Bullock"},{"id":10,"firstName":"Sandra","lastName":"Bullock"}]

5. curl -X "DELETE" -d 'lastName=Bullock' http://localhost:8080/demo/deleteCustomer  #COMMAND  
   should Result in  
   []

6