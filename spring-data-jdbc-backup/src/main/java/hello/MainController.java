package hello;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path="/demo")
public class MainController {

    public static final Logger LOGGER= LoggerFactory.getLogger(MainController.class);

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping(path="/addCustomer")
    public @ResponseBody String addCustomer(@RequestParam String firstName, @RequestParam String lastName){
        customerRepository.createCustomer(firstName,lastName);
        return customerRepository.getCustomerIdByFirstNameAndLastname(firstName,lastName);
    }

    @GetMapping(path="/allCustomers")
    public @ResponseBody Iterable<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }


    /*@GetMapping(path="addUser")
    public @ResponseBody String addUser(@RequestParam String firstName, @RequestParam String lastName){
        User user=new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userRepository.save(user);
        return user.getId().toString();
    }

    @GetMapping(path="/allUsers")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }*/


    /*@GetMapping(path="/addMongoCustomer")
    public @ResponseBody
    String addCustomer(@RequestParam String firstName, @RequestParam String lastName) {
        // This returns a JSON or XML with the users
        MongoCustomer p=new MongoCustomer(firstName,lastName);
        customerRepository.save(p);
        return p.getId();
    }

    @GetMapping(path="/getCustomersByFirstName")
    public @ResponseBody
    List<MongoCustomer> getMongoCustomersWithFirstName(@RequestParam String firstName) {
        // This returns a JSON or XML with the Customers
        return customerRepository.findByFirstName(firstName);
    }

    @GetMapping(path="/getCustomersByLastName")
    public @ResponseBody
    List<MongoCustomer> getMongoCustomersWithLastName(@RequestParam String lastName) {
        // This returns a JSON or XML with the Customers
        return customerRepository.findByLastName(lastName);
    }*/

}
