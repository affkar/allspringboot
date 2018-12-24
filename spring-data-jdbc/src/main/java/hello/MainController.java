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

}
