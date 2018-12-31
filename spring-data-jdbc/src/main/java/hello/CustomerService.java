package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerService {

    public static final Logger LOGGER= LoggerFactory.getLogger(MainController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    public
    String addCustomer(String firstName,String lastName){
        customerRepository.createCustomer(firstName,lastName);
        return customerRepository.getCustomerIdByFirstNameAndLastname(firstName,lastName);
    }

    public Iterable<Customer> getAllCustomers() {
        return customerRepository.getAllCustomers();
    }


    public void deleteCustomersByFirstName(String firstName){
        customerRepository.deleteCustomersByFirstName(firstName);
    }

    public void deleteCustomersByLastName(String lastName){
        customerRepository.deleteCustomersByLastName(lastName);
    }

    public void deleteCustomerById(Long id){
        customerRepository.deleteCustomerById(id);
    }

    public void deleteAllCustomers(){
        customerRepository.deleteAllCustomers();
    }


    public void dropDBAndCreateNewData() {
        LOGGER.info("Running DDLs now!");

        jdbcTemplate.execute("DROP TABLE IF EXISTS Customer");
        jdbcTemplate.execute("CREATE TABLE Customer ( customer_id INTEGER AUTO_INCREMENT, first_name varchar(255) , last_name varchar(255), primary key(customer_id))");

        List<Object[]> collect = Arrays.asList("Brad Pitt,Tom Hanks,Harrison Ford,Russell Crowe,Jude Law".split(",")).stream()
                .map((it) -> it.split(" "))
                .collect(Collectors.toList());

        collect.forEach(it->{LOGGER.info("Insert a Customer - FirstName {} and LastName {}", it[0],it[1]);});
        jdbcTemplate.batchUpdate("insert into Customer (first_name, last_name) values(?,?)", collect);

        LOGGER.info("Querying for User with Lastname Law");
        List<Customer> result = jdbcTemplate.query("select * from Customer where last_name=?", new Object[]{"Law"}, (rs, rowNum) -> new Customer(rs.getLong("customer_id"), rs.getString("first_name"), rs.getString("last_name")));
        LOGGER.info("Results are {}", result);

        LOGGER.info("Complete!!!");
    }
}
