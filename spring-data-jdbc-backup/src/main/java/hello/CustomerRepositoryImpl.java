package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("customerRepository")
public class CustomerRepositoryImpl implements CustomerRepository{

    public static final Logger LOGGER= LoggerFactory.getLogger(MainController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String getCustomerIdByFirstNameAndLastname(String firstName, String lastName) {
        List<Object[]> result = jdbcTemplate.query("select * from CUSTOMERS where first_name=? and last_name=?", new Object[]{firstName, lastName}, (rs, rowNum) -> new Object[]{rs.getLong("customer_id"), rs.getString("first_name"), rs.getString("last_name")});
        LOGGER.info("Results are {}", result);
        return result.get(0)[0].toString();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return jdbcTemplate.query("select * from CUSTOMERS", (rs, rowNum) -> new Customer(rs.getLong("customer_id"), rs.getString("first_name"), rs.getString("last_name")));
    }

    @Override
    public void createCustomer(String firstName, String lastName) {
        LOGGER.info("Insert a Customer - FirstName {} and LastName {}", firstName,lastName);
        jdbcTemplate.update("insert into CUSTOMERS (first_name, last_name) values(?,?)", new Object[]{firstName, lastName});
    }
}
