package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("customerRepository")
public class CustomerRepositoryImpl implements CustomerRepository{

    public static final Logger LOGGER= LoggerFactory.getLogger(MainController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String getCustomerIdByFirstNameAndLastname(String firstName, String lastName) {
        List<Object[]> result = jdbcTemplate.query("select * from Customer where first_name=? and last_name=?", new Object[]{firstName, lastName}, (rs, rowNum) -> new Object[]{rs.getLong("customer_id"), rs.getString("first_name"), rs.getString("last_name")});
        LOGGER.info("Results are {}", result);
        return result.get(0)[0].toString();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return jdbcTemplate.query("select * from Customer", (rs, rowNum) -> new Customer(rs.getLong("customer_id"), rs.getString("first_name"), rs.getString("last_name")));
    }

    @Override
    public void deleteCustomersByFirstName(String firstName) {
        jdbcTemplate.update("delete from Customer where first_Name=?",new Object[]{firstName});
    }

    @Override
    public void deleteCustomersByLastName(String lastName) {
        jdbcTemplate.update("delete from Customer where last_Name=?",new Object[]{lastName});
    }

    @Override
    public void deleteCustomerById(Long id) {
        jdbcTemplate.update("delete from Customer where Customer_ID=?",new Object[]{id});
    }

    @Override
    public void deleteAllCustomers() {
        jdbcTemplate.execute("delete from Customer");
    }

    @Override
    public Customer getCustomerById(Long id) {
            List<Object[]> result = jdbcTemplate.query("select * from Customer where Customer_ID=?", new Object[]{id}, (rs, rowNum) -> new Object[]{rs.getLong("customer_id"), rs.getString("first_name"), rs.getString("last_name")});
            LOGGER.info("Results are {}", result);
            if(result.isEmpty()){
                throw new DataRetrievalFailureException("Can't find Customer with ID "+id);
            }
            return new Customer(Long.valueOf(result.get(0)[0].toString()), result.get(0)[1].toString(), result.get(0)[2].toString());
    }

    @Override
    public void createCustomer(String firstName, String lastName) {
        LOGGER.info("Insert a Customer - FirstName {} and LastName {}", firstName,lastName);
        jdbcTemplate.update("insert into Customer (first_name, last_name) values(?,?)", new Object[]{firstName, lastName});
    }
}
