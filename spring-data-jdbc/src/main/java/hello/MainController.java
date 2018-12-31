package hello;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping(path="/demo")
public class MainController {

    public static final Logger LOGGER= LoggerFactory.getLogger(MainController.class);

    @Autowired
    private CustomerService customerService;

    @PostMapping(path="/addCustomer")
    public @ResponseBody String addCustomer(@RequestParam String firstName, @RequestParam String lastName){
        return customerService.addCustomer(firstName,lastName);
    }

    @GetMapping(path="/allCustomers")
    public @ResponseBody Iterable<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(path="/reload")
    public @ResponseBody void reload() {
        customerService.dropDBAndCreateNewData();
    }

    @DeleteMapping(path="/deleteCustomer")
    public @ResponseBody String deleteCustomer(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) Long id) {
        if (!StringUtils.isEmpty(firstName)) {
            customerService.deleteCustomersByFirstName(firstName);
            return "deleted CustomersByFirstName";
        } else if (!StringUtils.isEmpty(lastName)){
            customerService.deleteCustomersByLastName(lastName);
            return "deleted CustomersByLastName";
        }else if(Objects.nonNull(id)){
            customerService.deleteCustomerById(id);
            return "deleted CustomersById";
        }else{
            customerService.deleteAllCustomers();
            return "deleted All Customers";
        }
    }


}
