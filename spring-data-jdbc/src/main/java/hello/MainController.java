package hello;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping(path="/spring-data-jdbc")
@Api(value = "Customer", description = "Manage Customer, - Add, Delete, Reload")
public class MainController {

    public static final Logger LOGGER= LoggerFactory.getLogger(MainController.class);

    @Autowired
    private CustomerService customerService;

    @PostMapping(path="/addCustomer", produces = "text/plain")
    @ApiOperation(value="Add a Customer", response = String.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message = "Customer has been added"),
            @ApiResponse(code=500, message = "There was an internal server error adding the customer")
    })
    public @ResponseBody String addCustomer(@RequestParam String firstName, @RequestParam String lastName){
        return customerService.addCustomer(firstName,lastName);
    }
    @PostMapping(path="/addCustomerObject", produces = "text/plain")
    public @ResponseBody String addCustomer(@RequestBody Customer customer){
        return customerService.addCustomer(customer.getFirstName(),customer.getLastName());
    }

    @GetMapping(path="/allCustomers", produces = "application/json")
    @ApiOperation(value="Get the list of Customers", response = Iterable.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message = "All Customers fetched successfully"),
            @ApiResponse(code=500, message = "There was an internal server error fetching customers")
    })
    public @ResponseBody Iterable<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(path="/getCustomer/{id}",produces = "application/json")
    @ApiOperation(value="Get Customer by ID", response = Customer.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message = "Customer retrieved successfully"),
            @ApiResponse(code=500, message = "There was an internal server error retrieving customer")
    })
    public ResponseEntity<Customer> retrieveCustomer(@PathVariable Long id){
        if(Objects.nonNull(id)){
            Customer customer=customerService.getCustomerById(id);
            return new ResponseEntity<>(customer,HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path="/reload")
    @ApiOperation(value="Reload Initial Customers")
    @ApiResponses(value={
            @ApiResponse(code=200, message = "Customers reloaded successfully"),
            @ApiResponse(code=500, message = "There was an internal server error reloading customers")
    })
    public @ResponseBody void reload() {
        customerService.dropDBAndCreateNewData();
    }

    @DeleteMapping(path="/deleteCustomer/{id}",produces = "text/plain")
    @ApiOperation(value="Delete Customer by ID", response = String.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message = "Customer deleted successfully"),
            @ApiResponse(code=500, message = "There was an internal server error deleting customer")
    })
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        if(Objects.nonNull(id)){
            customerService.deleteCustomerById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path="/deleteCustomer",produces = "text/plain")
    @ApiOperation(value="Delete Customer with one or all of the parameters", response = String.class)
    @ApiResponses(value={
            @ApiResponse(code=200, message = "Customers deleted successfully"),
            @ApiResponse(code=500, message = "There was an internal server error deleting customers")
    })
    public @ResponseBody String deleteCustomer(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
        if (!StringUtils.isEmpty(firstName)) {
            customerService.deleteCustomersByFirstName(firstName);
            return "deleted CustomersByFirstName";
        } else if (!StringUtils.isEmpty(lastName)){
            customerService.deleteCustomersByLastName(lastName);
            return "deleted CustomersByLastName";
        }else{
            customerService.deleteAllCustomers();
            return "deleted All Customers";
        }
    }
}
